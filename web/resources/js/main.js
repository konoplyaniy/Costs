/*Menu*/

/* Open when someone clicks on the span element */
function openNav() {
    document.getElementById("navigation").style.width = "100%";
}

/* Close when someone clicks on the "x" symbol inside the overlay */
function closeNav() {
    document.getElementById("navigation").style.width = "0%";
}

/*end menu*/

function showHideForm() {
    var searchForm = document.getElementById("search-purchases-form");
    var searchButton = document.getElementById("show-hide-search")
    if (searchForm.style.display === "none") {
        searchForm.style.display = "block";
        searchButton.innerHTML = "Hide search form";
    } else {
        searchForm.style.display = "none";
        searchButton.innerHTML = "Show search form";
    }
}

function checkError() {
    var error = document.getElementById('dateError');
    if (null != error && error.innerHTML.length >0){
        document.getElementById('show-hide-search').click();
    }
}

function sync() {
    var submitBtn = document.getElementById('synchronize');
    submitBtn.click();
}

/*
$(function () {

    $("#dialog").dialog({
        autoOpen: false,
        modal: true
    });

    $("#myButton").on("click", function (e) {
        e.preventDefault();
        $("#dialog").dialog("open");
    });

});*/
