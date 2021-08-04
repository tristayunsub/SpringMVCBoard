function deleteBoardConfirm(){
    if (confirm("정말 삭제하시겠습니까?") == true) {
        $.ajax({
            beforeSend: function(xhr){
                xhr.setRequestHeader(header,token);
            },
            url: "/board/delete",
            data: {"boardId":boardId},
            type: "DELETE",
            cache:false,
            success: function (){
                location.href='/board/list';
                alert("삭제되었습니다.");
            }
        })
    } else{
        return false;
    }
}
function deleteComment(obj){
    var commentId = obj.parentNode.id;
    var comment = {
        boardId : boardId,
        commentId : commentId
    }
    var jsonData = JSON.stringify(comment);
    if (confirm("댓글을 삭제하시겠습니까?") == true){
        $.ajax({
            beforeSend: function(xhr){
                xhr.setRequestHeader(header,token);
            },
            url: "/comment/delete/"+boardId,
            type:"post",
            dataType: "text",
            contentType: "application/json; charset-utf-8",
            data:jsonData,
            success: function(data){
                $('#commentDiv').replaceWith(data);
            },
            error:
                // function(request,status,error){
                // alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);}
                function (){
                location.href='/login?redirectURL=/board/list/'+boardId;
                alert("로그인해주세요");
            }
        });
        return true;
    }else{
        return false;
    }
}
function editForm(){
    location.href='/board/edit/'+boardId;
}
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
        },
        error:
            // function(request,status,error){
            // alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            function (){
            location.href='/login?redirectURL=/board/list/'+boardId;
            alert("로그인해주세요");
        }
    });
}
function addReply(obj){
     if($("#replyInput"+obj).val()==""){
         $('.comment'+obj).removeClass("appear");
         $('.comment'+obj).addClass('disappear');
         return;
     }
    var comment = {
        content : $("#replyInput"+obj).val(),
    }
    var jsonData = JSON.stringify(comment);

    $.ajax({
        beforeSend: function(xhr){
            xhr.setRequestHeader(header,token);
        },
        type:"post",
        url: "/comment/reply/add/"+boardId+"/"+obj,
        dataType:"text",
        contentType: "application/json; charset-utf-8",
        data:jsonData,
        success: function(data){
            $('#commentDiv').replaceWith(data);
        },
        error:
            // function(request,status,error){
            // alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
            function (){
            location.href='/login?redirectURL=/board/list/'+boardId;
            alert("로그인해주세요");
        }
    });
}

function deleteReply(obj){
    var rplId = obj.parentNode.id.split('_')[1];
    var reply = {
        rplId : rplId
    }
    var jsonData = JSON.stringify(reply);
    if (confirm("댓글을 삭제하시겠습니까?") == true){
        $.ajax({
            beforeSend: function(xhr){
                xhr.setRequestHeader(header,token);
            },
            url: "/comment/reply/delete/"+boardId+"/"+rplId,
            type:"post",
            dataType: "text",
            contentType: "application/json; charset-utf-8",
            data:jsonData,
            success: function(data){
                $('#commentDiv').replaceWith(data);
            },
            error:
            function(request,status,error){
            alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);}
            //     function (){
            //         location.href='/login?redirectURL=/board/list/'+boardId;
            //         alert("로그인해주세요");
            //     }
        });
        return true;
    }else{
        return false;
    }
}

function replyForm(obj){

    if($('.comment'+obj).hasClass('appear')){
        $('.comment'+obj).removeClass("appear");
        $('#replyInput').removeClass("appear");
        $('.comment'+obj).addClass('disappear');
        $('#replyInput').addClass("disappear");
    }else if($('.comment'+obj).hasClass('disappear')){
        $('.comment'+obj).removeClass('disappear');
        $('.comment'+obj).addClass('appear');
        $('#replyInput').removeClass("disappear");
        $('#replyInput').addClass("appear");
    }else{
        $('.comment'+obj).addClass('appear');
        $('#replyInput').addClass("appear");
    };


}

function addRecommend(obj){
    var recommend = {
        boardId : boardId,
        likeId : loginUserId
    }
    var jsonData = JSON.stringify(recommend);
    console.log(jsonData);
    if (confirm("해당글을 추천하시겠습니까?") == true){
        $.ajax({
            beforeSend: function(xhr){
                xhr.setRequestHeader(header,token);
            },
            url: "/board/recommend",
            type:"post",
            dataType: "text",
            contentType: "application/json; charset-utf-8",
            data:jsonData,
            success: function(data){
                $('#recommendDiv').replaceWith(data);
            },
            error:
                // function(request,status,error){
                //     alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);}
                function (){
                    alert("추천은 1번만 가능합니다.");
                }
        });
        return true;
    }else{
        return false;
    }

}




// $(function(){
//     $('#likeButton').mousedown(function(){
//         if($('#likeButton').hasClass('bi-hand-thumbs-up')){
//             $('#likeButton').removeClass('bi-hand-thumbs-up')
//             $('#likeButton').addClass('bi-hand-thumbs-up-fill')
//         }else{
//             $('#likeButton').removeClass('bi-hand-thumbs-up-fill')
//             $('#likeButton').addClass('bi-hand-thumbs-up')
//         }
//     }).click(function() {
//         return false;
//     });
// });
