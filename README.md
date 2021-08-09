# SpringMVC Board Project ![Build Status](https://travis-ci.com/hsp0404/SpringMVCBoard.svg?branch=master)
## 목차
1. [**개발 목적**](#개발-목적)

2. [**개발 환경**](#개발-환경)

3. [**프로젝트 구조**](#프로젝트-구조)

4. **기능**

5. **느낀점**
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


---

> ### 게시판 기능
>> 글쓰기

기본적인 제목/내용을 작성하고, 이미지 업로드 (미리보기 기능) 을 구현했습니다.

![이미지 업로드](https://user-images.githubusercontent.com/73703641/128662118-70fb66bd-e5f2-4710-a67a-e2dde8f16680.gif)

미리보기 기능은 JavaScript로 구현했으며 /resources/templates/writeForm.html에 작성되어 있습니다.

---
이미지 파일은 서버 폴더 밖 외부에 저장되도록 했으며   
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



