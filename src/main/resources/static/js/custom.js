$(document).ready(function () {
    $('.sidenav ul li:first').addClass('active').removeClass('sidenav ul li a');
    $('.tab-content:not(:first)').hide();
    $('.sidenav ul li a').click(function (event) {
        event.preventDefault();
        var content = $(this).attr('href');
        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');
        $(content).show();
        $(content).siblings('.tab-content').hide();
    });
});

/**********************modal****************************/
