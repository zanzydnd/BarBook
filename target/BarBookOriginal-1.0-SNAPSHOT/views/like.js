window.onload = function () {
    var inp_likedCocktId = document.querySelector('input[name=likedCocktId]');
    console.log(inp_likedCocktId);
    var inp_user_id = document.querySelector('input[name=user_id]');
    console.log(inp_user_id);

    document.querySelector('#submit').onclick = function () {
        var params = 'likedCocktId=' + inp_likedCocktId.value + '&' + 'user_id=' + inp_user_id.value;
        ajaxPost(params);
    }
}

function ajaxPost(params) {
    var request = new XMLHttpRequest();

    request.onreadystatechange = function () {
        if (request.readyState == 4 && request.status == 200) {
            if (request.responseText === 'Already') {
                document.querySelector('#err').innerHTML = 'Вы уже оставляли лайк';
            } else {
                document.querySelector('#rate').innerHTML = request.responseText;
            }
        }
        request.open('POST', '/BarBookOriginal_war/cocktail');
        request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        request.send(params);
    }
}