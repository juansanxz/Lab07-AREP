function loadGetPalMsg() {
        let value = document.getElementById("pal").value;
        const xhttp = new XMLHttpRequest();
        xhttp.onload = function() {
           console.log(this.status);
           if (this.status === 200) {
               document.getElementById("palAnwser").textContent = this.responseText;
           }
        }
        xhttp.open("GET", "/palindrome?ispalindrome="+value);
        xhttp.send();
}
