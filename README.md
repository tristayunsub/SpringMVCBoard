# SpringMVC Board Project ![Build Status](https://travis-ci.com/hsp0404/SpringMVCBoard.svg?branch=master)
## 목차
1. [**개발 목적**](#개발-목적)

2. [**개발 환경**](#개발-환경)

3. [**프로젝트 구조**](#프로젝트-구조)

4. [**기능**](#기능)

5. [**느낀점**](#느낀점)
---
## 개발목적
* Web 백엔드 개발자가 되기 위해 가장 보편적인 Spring의 기본기를 다지기 위함입니다.
* 현재는 모든 지식이 완성되지 않은 상태에서 계속 공부 하며 기능을 추가하고 있습니다.
---
## 개발 환경
* bootstrap 5.0.2
* thymeleaf 
* Java 11
* Oracle 11g (Oracle Cloud / CentOS)
* mybatis
* lombok
* Spring Boot 2.5.3
* Gradle
* IntellJ 2021-01
* Amazon Web Service (Amazon Linux)


[목차](#목차)

---
## 프로젝트 구조
![ccccc (1)](https://user-images.githubusercontent.com/73703641/128649111-36f9c367-6231-41d7-99e4-48a1e49293b8.png)
---
![DB](https://user-images.githubusercontent.com/73703641/128659623-3be2d304-6a87-4580-b98c-0b5768f980cf.png)

회원이 삭제되더라도 글은 남겨놓고록 했고, 게시글이 삭제되면 댓글,대댓글,파일,추천 모두 사라지게 CASCADE 구성을 했습니다.

데이터베이스는 오라클 클라우드 프리티어 가상 인스턴스를 통해 구축했습니다.

![오라클클라우드](https://user-images.githubusercontent.com/73703641/128668428-8357063c-a9f3-4b56-b5ce-6c52a0b00def.png)
![오라클클라우드2-수정](https://user-images.githubusercontent.com/73703641/128668740-795bd169-7e80-4122-96ff-8017325ecc40.png)

특별한 이유가 있는 것은 아니고 M1 Mac을 통해 실습을 진행했는데 로컬에 도저히 Oracle을 깔 수 있는 방법이 없어   
리눅스 공부 / AWS 배포 전 가상 인스턴스 공부를 겸하기 위해 이 방법을 택했습니다.

---
![구조1](https://user-images.githubusercontent.com/73703641/128649186-b76cfd3f-a7fc-42fe-bf24-01db1590435d.png)
![구조2](https://user-images.githubusercontent.com/73703641/128649188-74fb06e7-7b9c-465b-9fad-634b3317fb79.png)

자주 사용되는 화면/JS/CSS는 빼서 사용했습니다.

[목차](#목차)

---

## 기능
> ### 회원가입/로그인
>> 회원가입

### 빈칸/Null 검증

![빈칸 검증](https://user-images.githubusercontent.com/73703641/128650238-b24543bd-888a-4fb5-8a14-30fe16e84f7f.gif)

스프링 Validated 기능을 활용하여 빈 값/Null이 Post될 시에 BindingResult에 에러를 담아 표시되도록 했습니다.
```java
    @PostMapping("/signup")
    public String signup(@Validated @ModelAttribute User user,
                         BindingResult bindingResult, HttpServletRequest request)
```
```java
public class User {
    @NotBlank
    private String userId;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    private int gender;
    private String birth;
    @Email
    @NotBlank
    private String email;
    private String phone;
    private Date signupDate;
}
```
---

### 중복 ID 검사
![이미 사용중인 아이디](https://user-images.githubusercontent.com/73703641/128650155-5a2749b9-7a2d-4789-9e09-055ceedea40f.gif)

동일한 ID가 존재할 때도 역시 에러가 표시되도록 했습니다.
```java
        List<User> userList = userService.getUserList();
        for (User users : userList) {
            if (user.getUserId().equals(users.getUserId())) {
                bindingResult.rejectValue("userId","existed");
                return "user/signupForm";
            }
        }
```

---
### 비밀번호 확인
![비밀번호 다름](https://user-images.githubusercontent.com/73703641/128650448-a92bda90-55e1-4579-95d6-0c4b0320986f.gif)

JavaScript를 통해서 비밀번호가 일치하는지 확인하도록 했습니다.

---
### 이메일 인증
![이메일인증](https://user-images.githubusercontent.com/73703641/128650784-d2df0de0-a347-484e-837d-9a1f7c304b09.gif)

```java
    @ResponseBody
    @RequestMapping("/sendMail")
    public String sendMail(@RequestBody SimpleMailMessage mail){
        Random random = new Random();
        int key = random.nextInt(8999) + 1000;
        mail.setSubject("hsp94.site 회원가입 인증메일 입니다.");
        mail.setText("인증번호 : "+String.valueOf(key)+"를 입력해주세요");
        mailSender.send(mail);
        return String.valueOf(key);
    }
```

1000~9999 사이의 난수를 생성해 입력한 이메일 주소로 전송하고 그 값을 다시 넘겨받습니다.

![이메일인증 메일](https://user-images.githubusercontent.com/73703641/128650805-79478527-8b2a-4c52-a218-44e46d67b52c.png)

![이메일인증 확인](https://user-images.githubusercontent.com/73703641/128650840-c6246769-8f4e-478c-a3be-f6aaaebfc167.gif)

---
![이메일인증필수](https://user-images.githubusercontent.com/73703641/128651068-71c1938a-4259-4b49-ba97-b9152e55db88.gif)

JavaScript를 통해서 이메일 인증이 되지 않았으면 Submit하지 않고 Return 하도록 했습니다.

---
### 회원가입 결과
![회원가입완료](https://user-images.githubusercontent.com/73703641/128651210-762d8fca-6e37-48f3-bd68-8d93e13b3db9.png)

Spring Security와 BCryptPassword를  통하여 암호화된  비밀번호가 DB에 저장되도록 했습니다.
```java
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }
```

---
> ### 회원가입/로그인
>> 로그인

Spring Security를 활용하여 로그인 인증을 구현했습니다.
```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                        //로그인 하지 않으면 게시물 목록, 회원가입, 로그인을 제외한 모든 페이지 접근 불가
                        .antMatchers("/","/board/list","/signup",
                        "/profile","/actuator/health").permitAll()
                        .anyRequest().authenticated()
                        .and()
                .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login/auth")
                        .permitAll()
                        .defaultSuccessUrl("/board/list")
                        .failureUrl("/login?error=true")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .and()
                .logout()
                        .deleteCookies("JSESSIONID,SPRING_SECURITY_REMEMBER_ME_COOKIE")
                        .permitAll();
        }
```
### 로그인 실패시

![로그인실패](https://user-images.githubusercontent.com/73703641/128661469-2afa65cc-eb32-47b9-b01c-549139a1668f.gif)


[목차](#목차)

---

> ### 게시판 기능
>> 글쓰기

기본적인 제목/내용을 작성하고, 이미지 업로드 (미리보기 기능) 을 구현했습니다.

![이미지 업로드](https://user-images.githubusercontent.com/73703641/128662118-70fb66bd-e5f2-4710-a67a-e2dde8f16680.gif)

미리보기 기능은 JavaScript로 구현했으며 /resources/templates/writeForm.html에 작성되어 있습니다.

---
이미지 파일은 서버 구동 폴더 밖 외부에 저장되도록 했으며   
DB에는 중복이 되지 않게 UUID를 이용한 난수 파일명과 업로드 파일명을 Insert했습니다.

### 이미지 DB 저장 결과
![파일 저장결과](https://user-images.githubusercontent.com/73703641/128662560-ba0a6c43-7567-495a-a41b-d49dab155248.png)

---

> ### 게시판 기능
>> 글 수정/삭제

Spring Security의 @AuthenticationPrincipal 어노테이션을 통해 로그인 정보를 가져와 자신의 글만 수정/삭제 가능하도록 했습니다.

```html
    <div class="col" th:if="${loginUser != null && loginUser.userId == board.writeId}">
      <button class="btn btn-primary float-xl-start" type="button" 
              th:onclick="|location.href='@{/board/edit/}${board.id}'|">글 수정</button>
      <span>   </span>
      <button class="btn btn-primary float-xl-start" type="button" 
              onclick="deleteBoardConfirm()">글 삭제</button>
    </div>
```
1차적으로 html에서 Model Attribute로 받은 로그인된 유저를 확인하고 자신의 글에만 글 수정/삭제 버튼이 나타나도록 했습니다.

하지만 주소창에 /edit 주소를 입력하여 접근할 수 있기 때문에

2차로 SpringSecurity를 통해 로그인 되어있지 않으면 /edit에 Get요청을 보내지 않고 로그인 창으로 리다이렉팅 되게  했습니다.

```java
    @GetMapping("/edit/{boardId}")
    public String boardEditForm(Model model, @PathVariable int boardId,
                                @AuthenticationPrincipal UserInfo loginUser, HttpServletResponse response) throws IOException {
        Board board = boardService.getBoard(boardId);
        if(!loginUser.getUserId().equals(board.getWriteId())){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("<script>alert('잘못된 접근입니다.'); history.go(-1);</script>");
            writer.flush();
            return null;
        }
        model.addAttribute("board", board);
        return "editForm";
    }
```
꼭 필요하지는 않지만 확실히 하기 위해 
Get요청에서 로그인된 유저를 확인하여 일치하지 않으면 이전 페이지로 돌아가도록 했습니다.


---
> ### 게시판 기능
>>조회수 기능

게시물에 대한 Get요청이 올때 DB에 있는 HIT 컬럼에 1을 더하도록 했습니다.   
중복방지를 위해 열람했던 게시물 목록을 쿠키로 저장했고 Controller단에서 이전에 봤던 게시물은 조회수를 증가하지 않도록 했습니다.

![뷰쿠키](https://user-images.githubusercontent.com/73703641/128663358-7fa86ee3-8509-44e0-9c59-9ab5c593d246.png)
```java
        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                //viewCookie를 불러와 구분자(-)로 나누어 열람한 게시물 목록 얻기
                if(cookie.getName().equals("viewCookie")){
                    String[] boardIdCookies = cookie.getValue().split("-");
                    for (String boardIdCookie : boardIdCookies) {
                        //현재 열람하려는 게시물 번호(boardId)가 viewCookie 목록에 있으면 return
                        if (boardIdCookie.equals(String.valueOf(boardId))) {
                            return "board";
                        }
                    }
                    String prevCookieValue = cookie.getValue();
                    cookie.setValue(prevCookieValue+"-"+boardId);
                    response.addCookie(cookie);
                    //조회수 증가 메서드 호출
                    boardService.hit(boardId);
                    return "redirect:/board/list/"+boardId;
                }
            }
        }
```

---

> ### 게시판 기능
>>댓글 / 대댓글

댓글 / 대댓글은 ajax 비동기 방식으로 구현했습니다.
![댓글대댓글](https://user-images.githubusercontent.com/73703641/128663750-2b51f0fd-2262-40ba-9dc8-49f6b603b4c7.gif)

```javascript
function addComment(){
    var comment = {
        content : $("#content").val(),
    }
    var jsonData = JSON.stringify(comment);
    $.ajax({
        beforeSend: function(xhr){
            xhr.setRequestHeader(header,token);
        },
        type:"post",
        url: "/comment/add/"+boardId,
        dataType:"text",
        contentType: "application/json; charset-utf-8",
        data:jsonData,
        success: function(data){
            $('#commentDiv').replaceWith(data);
        }
    });
}
```

댓글 부분 div 태그에 id를 지정하고, ajax 통신이 성공하면 이 div가 교체될 수 있도록 작성했습니다.

---

Spring Security의 @AuthenticationPrincipal 어노테이션을 통해 로그인 정보를 가져와 자신의 댓글만 삭제할 수 있도록 했습니다.
![댓글 삭제](https://user-images.githubusercontent.com/73703641/128664079-d64ec604-ef52-495b-9f15-ee039ca43610.gif)

---

> ### 게시판 기능
>>추천 기능

추천 기능 또한 ajax 통신으로 구현했으며 중복을 방지하기 위해   
데이터베이스에 '추천한 게시물'과 '아이디'를 저장하는 테이블을 만들어서 관리했습니다.

![게시물 추천](https://user-images.githubusercontent.com/73703641/128665032-91c12eb2-da2b-47f7-8ad9-2d842ee096b2.gif)

---
> ### 게시판 기능
>>페이징
 
페이징 기능은 thymeleaf를 적극 활용하였고 처음 페이지와 마지막 페이지에 각각 Previous / Next 버튼이 비활성화 되도록 했습니다.

![페이징](https://user-images.githubusercontent.com/73703641/128665372-ef8b2309-9d6f-43d8-938d-eeda639ebf0d.gif)

```html
    <nav aria-label="Page navigation example">
      <ul class="pagination justify-content-center">
        <li th:if="${query==1}" class="page-item disabled">
          <a class="page-link" href="#" tabindex="-1">Previous</a>
        </li>
        <li th:unless="${query==1}" class="page-item">
          <a class="page-link" th:href="@{/board/list(p=${query}-1)}" href="#" tabindex="-1">Previous</a>
        </li>
        <th:block th:each="num: ${#numbers.sequence(1,lastNum)}">
          <li th:if="${num==query}" class="page-item active">
            <a th:text="${num}" th:href="@{/board/list(p=${num})}" class="page-link" href="#"></a>
          </li>
          <li th:unless="${num==query}" class="page-item">
            <a th:text="${num}" th:href="@{/board/list(p=${num})}" class="page-link" href="#"></a>
          </li>
        </th:block>
        <li th:if="${query==lastNum}" class="page-item disabled">
          <a class="page-link" href="#">Next</a>
        </li>
        <li th:unless="${query==lastNum}" class="page-item">
          <a class="page-link" th:href="@{/board/list(p=${query}+1)}"  href="#">Next</a>
        </li>
      </ul>
    </nav>
```

---
> ### 게시판 기능
>>게시물 검색

게시물 검색은 제목, 제목+내용 , 글쓴이로 검색할 수 있도록 했으며 일치하는게 없을 때에는 검색 결과가 없다는 메시지를 출력하도록 했습니다.

![게시물검색](https://user-images.githubusercontent.com/73703641/128665712-5fdd65c6-a0e5-40b9-8cd4-78f40c2fa060.gif)


[목차](#목차)


---

> ### 보안 / 배포
>> 보안

 데이터베이스 접속 정보, 이메일 정보 등은 외부에 저장하고 시작할때 불러들일 수 있도록 했습니다.

```java
public class PracticePrjApplication {

	public static final String APPLICATION_LOCATIONS_WIN =
           //윈도우에서 테스트 할 경우의 구성파일 경로 
            "spring.config.location=classpath:application.properties,C:/Users/user/real-application.yml";
	public static final String APPLICATION_LOCATIONS_EC2 =
            //실제 서버에서 구현할 경우의 구성파일 경로
            "spring.config.location=classpath:application.properties,/app/.../real-application.yml";

	public static void main(String[] args) {
		File f = new File("C://Users/user/real-application.yml");
		//윈도우 경로에 파일이 존재하지 않는경우 (aws 서버)에는 알맞은 경로를 통해 실행한다.
		if (f.exists()) {
			new SpringApplicationBuilder(PracticePrjApplication.class)
                    .properties(APPLICATION_LOCATIONS_WIN).run(args);
		} else {
			new SpringApplicationBuilder(PracticePrjApplication.class)
                    .properties(APPLICATION_LOCATIONS_EC2).run(args);
		}
	}
}
```
이전 커밋들에서 빌드 파일안에 각종 비밀번호들이 포함되게 커밋했습니다. 서버 보안 뿐만 아니라
제 개인정보 보안에도 문제가 생길것 같아 비밀번호를 모두 교체하고 위와 같이 변경했습니다.

yml 설정파일
````yaml
---
spring:
  profiles: set1
  #데이터베이스 접속정보
  datasource:
    url: jdbc:oracle:thin:@152.70...:1521:xe
    driver-class-name: oracle.jdbc.OracleDriver
    username: ...
    password: ...
  #이메일 전송 정보
  mail:
    username: hsp4567@gmail.com
    password: ...
server:
  port: 8001
  
logging:
    level:
        root: error
    file:
        path: /home/.../log
        

---
#무중단 배포를 위해 set1, set2로 나누었습니다.
spring:
  profiles: set2
  datasource:
    url: jdbc:oracle:thin:@152.70...:1521:xe
    driver-class-name: oracle.jdbc.OracleDriver
    username: ...
    password: ...
  mail:
    username: hsp4567@gmail.com
    password: ...

server:
  port: 8002

logging:
  level:
    root: error
  file:
    path: /home/.../log
````


[목차](#목차)

---

> ### 보안 / 배포
>> 배포

배포는 Amazon Web Service 프리티어를 통해 배포했습니다. Amazon Linux 인스턴스를 사용했습니다.

---

Travis, Amazon S3, Amazon CodeDeploy를 통한 무중단 배포 시스템을 구축했습니다.

1. GitHub에 Push 하면 Travis에서 빌드합니다.

![트래비스](https://user-images.githubusercontent.com/73703641/128670213-74507d2f-e382-4ec0-9ec2-41a168700bbc.png)

2. 압축하여 S3 버킷에 저장합니다.

![S3](https://user-images.githubusercontent.com/73703641/128670211-3334709c-9caf-4240-b509-2b1aa772e9df.png)
![s3-2](https://user-images.githubusercontent.com/73703641/128670209-1bdc1811-e734-429b-a8e1-17c3118ba854.png)

3. CodeDeploy를 통해 서버로 실행 파일을 업데이트 합니다.
   
![codedeploy](https://user-images.githubusercontent.com/73703641/128670208-1bf70aa2-db73-4f86-9736-eafab94e9147.png)
   
4. 자동으로 셀 스크립트를 실행하도록 설정합니다.
   
![배포후](https://user-images.githubusercontent.com/73703641/128670563-311d1a8d-98cd-430f-a0a7-d1960548f045.png)
![배포후2](https://user-images.githubusercontent.com/73703641/128670558-3476b0e3-f54c-49e3-8e69-c3aa0a4e12f8.png)

5. 자동화 스크립트

```shell
#!/bin/bash
BASE_PATH=/home/.../nonstop
BUILD_PATH=$(ls $BASE_PATH/SpringMVCBoard/.../*.war)
WAR_NAME=$(basename $BUILD_PATH)

# build 파일 복사
DEPLOY_PATH=$BASE_PATH/war/
cp $BUILD_PATH $DEPLOY_PATH

#현재 구동중인 Set 확인
CURRENT_PROFILE=$(curl -s http://localhost/profile)
echo "> $CURRENT_PROFILE"

```
#### /profile는 현재 실행환경을 보여주도록 Mapping 했습니다

```java
    @GetMapping("/profile")
    public String getProfile(){
            return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("");
            }
```


```shell
# 쉬고 있는 set 찾기: set1이 사용중이면 set2가 쉬고 있고, 반대면 set1이 쉬고 있음
if [ $CURRENT_PROFILE == set1 ]
then
  IDLE_PROFILE=set2
  IDLE_PORT=8002
elif [ $CURRENT_PROFILE == set2 ]
then
  IDLE_PROFILE=set1
  IDLE_PORT=8001
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> set1을 할당합니다. IDLE_PROFILE: set1"
  IDLE_PROFILE=set1
  IDLE_PORT=8001
fi

# war 파일 교체
IDLE_APPLICATION=$IDLE_PROFILE-SpringMVCBoard.war
IDLE_APPLICATION_PATH=$DEPLOY_PATH$IDLE_APPLICATION

ln -Tfs $DEPLOY_PATH$WAR_NAME $IDLE_APPLICATION_PATH

echo "> $IDLE_PROFILE 에서 구동중인 애플리케이션 pid 확인"
IDLE_PID=$(pgrep -f $IDLE_APPLICATION)

if [ -z $IDLE_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  sudo kill -15 $IDLE_PID
  sleep 5
fi

echo "> $IDLE_PROFILE 배포"
nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE $IDLE_APPLICATION_PATH &

echo "> $IDLE_PROFILE 10초 후 Health check 시작"
echo "> curl -s http://localhost:$IDLE_PORT/actuator/health "
sleep 10

```
스프링부트의 Actuator를 활용하여 서버가 정상적으로 작동하는지 테스트합니다.

![액츄에이터](https://user-images.githubusercontent.com/73703641/128672018-f15d516d-c31e-4dd0-91e5-a5ff55265a02.png)


```shell

for retry_count in {1..4}
do
  response=$(curl -s http://localhost:$IDLE_PORT/actuator/health)
  up_count=$(echo $response | grep 'UP' | wc -l)

  if [ $up_count -ge 1 ]

  then # $up_count >= 1 ("UP" 문자열이 있는지 검증)
      echo "> Health check 성공"
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 status가 UP이 아닙니다."
      echo "> Health check: ${response}"
  fi

  if [ $retry_count -eq 4 ]
  then
    echo "> Health check 실패. "
    echo "> Nginx에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 3
done

echo "> 스위칭"
sleep 10

#nginx가 배포 시점에 바라보는 Profile을 자동으로 변경해주도록 하는 스크립트 실행
/home/.../nonstop/switch.sh

```

nginx를 동적 프록시 환경으로 구축했습니다.

service-url.inc
```
set $service_url http://127.0.0.1:8001;
```

nginx.conf
```
        include /etc/nginx/conf.d/service-url.inc;

        location / {
                proxy_pass $service_url;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }
```

nginx Port 변환을 자동으로 해주는 스크립트
```shell
#!/bin/bash
echo "> 현재 구동중인 Port 확인"
CURRENT_PROFILE=$(curl -s http://localhost/profile)

# 쉬고 있는 set 찾기: set1이 사용중이면 set2가 쉬고 있고, 반대면 set1이 쉬고 있음
if [ $CURRENT_PROFILE == set1 ]
then
  IDLE_PORT=8002
elif [ $CURRENT_PROFILE == set2 ]
then
  IDLE_PORT=8001
else
  echo "> 일치하는 Profile이 없습니다. Profile: $CURRENT_PROFILE"
  echo "> 8001을 할당합니다."
  IDLE_PORT=8001
fi

echo "> 전환할 Port: $IDLE_PORT"
echo "> Port 전환"
echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" |sudo tee /etc/nginx/conf.d/service-url.inc

PROXY_PORT=$(curl -s http://localhost/profile)
echo "> Nginx Current Proxy Port: $PROXY_PORT"

echo "> Nginx Reload"
sudo service nginx reload
```

---

#### HTTPS

certbot / let'sEncrypt 를 사용하여 SSL 인증을 받았습니다.

![https1](https://user-images.githubusercontent.com/73703641/128672882-b7dc4092-e6da-48ab-b1f4-fdf2e8ff1b1a.png)
![https2](https://user-images.githubusercontent.com/73703641/128672885-a3fbffa4-cfb2-4704-b8f3-dbd646e2c909.png)

---

## 느낀점

1. 직전 회사에서 개발직은 아니었지만 작은 회사라 제가 직접 100명정도 사용하는 출퇴근 기록/조회기를 만들어 운영해본적이 있습니다.
   이 기회를 통해서 각종 검증에 대한 중요성을 알게 되었고, 회원가입부터 로그인, 글쓰기 등 전반적인 분야에서 빈값 검증이나 정규식 검증을 구현해보려고 노력했습니다.
   하지만 부족한 부분도 많다고 생각하고 완벽하게 하기는 어려웠습니다. 각종 기능 구현보다 버그 수정이나 검증 구현에서 고민해야 하는것이 더 많다고 느꼈습니다.
      


2. 제가 대부분 부트스트랩을 활용했지만 제가 원하는대로 조금씩 수정하는 것이 쉽지는 않았습니다. 완전히 백엔드 포지션으로 일하지 않는 이상 HTML, CSS 공부를 해야한다고 느꼈습니다.


3. XSS 방어 라이브러리를 적용시켜보고 싶었지만 어려움을 느꼈습니다. 좀 더 공부하여 적용시켜볼 생각입니다.



4. 협업 할 정도의 프로젝트 규모는 아니지만 협업의 경험을 쌓을 수 없어서 아쉬웠습니다.


5. 검색의 중요성을 느꼈습니다. 제가 궁금해 하는것들은 이미 누군가 이미 겪어본 일이고 그저 문제만 해결하려고만 하지 않고 해결 방법을 이해한다면 제 것이 될 수 있다고 생각했습니다.



3. 본격적으로 회사를 퇴사하고 프로그래밍 공부를 시작한지 2개월 (6.7 시작 ~ 현재 (8.9))이 겨우 지나가는 짧은 공부 기간 동안 많은걸 느꼈습니다. 후회없는 선택이었고, 적성에 잘 맞다는 것입니다. 제 인생에서 공부가 재밌게 느껴지는 것은 이번이 처음인것 같습니다.