/*
   Building a Bootstrap Contact Form Using PHP and AJAX
   http://webdesign.tutsplus.com/tutorials/building-a-bootstrap-contact-form-using-php-and-ajax--cms-23068
   https://github.com/tutsplus/building-a-bootstrap-contact-form-using-php-and-ajax
 */

(function() {

    $("#formAuthor").validator().on("submit", function (event) {
        if (event.isDefaultPrevented()) {
            // handle the invalid form
            formError();
            submitMsg(false, "Please correct any errors.");
        } else {
            // everything looks good!
            // prevent the default event from occuring,
            // we submit the form manually using ajax.
            event.preventDefault();
            submitAuthor();
        }
    });

    function submitAuthor() {
        // Initialize variables with form content
        var id = $("#au_id").val();
        var fname = $("#au_fname").val();
        var lname = $("#au_lname").val();
        var address = $("#au_address").val();
        var city = $("#au_city").val();
        var state = $("#au_state").val();
        var zip = $("#au_zip").val();
        var phone = $("#au_phone").val();
        var message = $("#message").val();

        // ajax call to submit form using POST
        $.ajax({
            type: "POST",
            url: "author.php",
            data: "id=" + id + "&fname=" + fname + "&lname=" + lname + "&address=" + address +
              "&city=" + city + "&state=" + state + "&zip=" + zip + "&phone=" + phone +
              "&message=" + message,
            error : function(xhr) {
                submitMsg(false, "Error: " + xhr.status + " " + xhr.statusText);
                console.log("submitAuthor: Ajax error");
            },
            success : function(result){
                // remove any newline characters
                var result = result.replace(/(\r\n|\n|\r)/gm,"");

                formSuccess();
                console.log("submitAuthor: success");

                // if (result == "success"){
                //     formSuccess();
                //     console.log("submitAuthor: success");
                // } else {
                //     formError();
                //     submitMsg(false, "Error: " + result);
                //     console.log("submitAuthor: error");
                // }
            }
        });
    }

    function formSuccess(){
        // $("#formAuthor")[0].reset();
        submitMsg(true, "Record updated!")
    }

    function formError(){
        $("#formAuthor").removeClass().addClass('shake animated').one('webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend', function(){
            $(this).removeClass();
        });
    }

    function submitMsg(valid, msg){
        var msgClasses;
        if (valid){
            msgClasses = "h3 col-sm-8 text-center text-success";
        } else {
            msgClasses = "h3 col-sm-8 text-center text-danger";
        }
        $("#message").removeClass().addClass(msgClasses).text(msg);
    }

})();

