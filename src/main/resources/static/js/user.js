let index = {
    init:function(){
        $("#btn-save").on("click",()=>{ //function(){} 대신에 ()=>{} 를 사용한 이유는 this 를 바인딩 하기 위해서이다. 
            this.save();
        });
    },

    save: function(){
        //alert('user의 save 함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val(),
        }

        //console.log(data);

        //ajax호출시 default가 비동기호출
        //ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert요청 !!
        $.ajax({
           type:"POST",
           url:"/blog/api/user",
           data:JSON.stringify(data),// http body데이터 
           contentType:"application/json; charset=utf-8", // body데이터가 어떤타입인지(MIME)
           dataType:"json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열인데 생긴게 json이라면 => javascript 오브젝트로 변경 
        }).done(function(resp){
            alert("회원가입이 완료되었습니다.");
           
           // location.href ="/blog";
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); 
    
    
    }
}

index.init();
