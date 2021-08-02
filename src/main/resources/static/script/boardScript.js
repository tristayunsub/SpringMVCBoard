function deleteBoardConfirm(){
    if (confirm("정말 삭제하시겠습니까?") == true) {
        $.ajax({
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
function deleteCommentConfirm(obj){
    console.log(obj);
    var commentId = obj.parentNode.id;
    var comment = {
        boardId : boardId,
        commentId : commentId
    }
    var jsonData = JSON.stringify(comment);
    console.log(jsonData);
    if (confirm("댓글을 삭제하시겠습니까?") == true){
        $.ajax({
            url: "/comment/delete/"+boardId,
            type:"post",
            dataType: "text",
            contentType: "application/json; charset-utf-8",
            data:jsonData,
            success: function(data){
                $('#commentDiv').replaceWith(data);
            },
            error: function (){
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
    if($("#content"))
    var comment = {
        content : $("#content").val(),
    }
    var jsonData = JSON.stringify(comment);
    $.ajax({
        type:"post",
        url: "/comment/add/"+boardId,
        dataType:"text",
        contentType: "application/json; charset-utf-8",
        data:jsonData,
        success: function(data){
            $('#commentDiv').replaceWith(data);
        },
        error: function (){
            location.href='/login?redirectURL=/board/list/'+boardId;
            alert("로그인해주세요");
        }
    });
}