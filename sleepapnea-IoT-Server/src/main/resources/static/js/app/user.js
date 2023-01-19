let index = {
    init: function(){
        $("#btn-user-update").on("click", ()=>{
            this.update();
        });
    },
    update: function(){
        var data = {
            username : $("#username").val(),
            realname : $("#realname").val(),
            email : $("#email").val(),
            address : $("#address"),
            phoneNum : $("#phoneNum").val(),
            c_phoneNum : $("#c_phoneNum").val()
        };

        $.ajax({
            //회원가입수행요청
            type: "POST",
            url: "/api/v1/updateProc",
            data:JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"

        }).done(function(resp){
            alert("회원수정이 완료되었습니다.");
            location.href="/";

        }).fail(function(error){
            alert("실패.");

            alert(JSON.stringify(error));
        });
    }


}
index.init();