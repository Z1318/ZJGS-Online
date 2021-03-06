<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var='currentname' value="${currentname}" scope="page"/>
<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset="utf-8" />

    <title>Cod报表系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="assets/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%=basePath%>assets/css/ace.min.css" />
    <link rel="stylesheet" href="<%=basePath%>assets/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="<%=basePath%>assets/css/ace-skins.min.css" />
    <link rel="stylesheet" href="<%=basePath%>assets/css/common.css" />
    <link rel="stylesheet" href="<%=basePath%>assets/css/input.css" />
    <script src="../assets/js/ace-extra.min.js"></script>
    <script src="../assets/js/datepicker/WdatePicker.js"></script>

</head>



<body>

<div class="navbar navbar-default" id="navbar">

    <script type="text/javascript">

        try{ace.settings.check('navbar' , 'fixed')}catch(e){}

    </script>



    <div class="navbar-container" id="navbar-container">

        <div class="navbar-header pull-left">

            <a href="#" class="navbar-brand">

                <small>

                    Cod报表系统

                </small>

            </a>

        </div>



        <div class="navbar-header pull-right" role="navigation">

            <ul class="nav ace-nav">

                <li class="light-blue">

                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">

								<span class="user-info">

									<small>欢迎光临,</small>

									${student_id}

								</span>



                        <i class="icon-caret-down"></i>

                    </a>



                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">

                        <li class="divider"></li>



                        <li>

                            <a href="/cod/quit">

                                <i class="icon-off"></i>

                                退出

                            </a>

                        </li>

                    </ul>

                </li>

            </ul><!-- /.ace-nav -->

        </div><!-- /.navbar-header -->

    </div><!-- /.container -->

</div>



<div class="main-container" id="main-container">

    <script type="text/javascript">

        try{ace.settings.check('main-container' , 'fixed')}catch(e){}

    </script>



    <div class="main-container-inner">

        <a class="menu-toggler" id="menu-toggler" href="#">

            <span class="menu-text"></span>

        </a>



        <div class="sidebar" id="sidebar">

            <script type="text/javascript">

                try{ace.settings.check('sidebar' , 'fixed')}catch(e){}

            </script>

            <ul class="nav nav-list">

                <c:forEach var="func" items="${funcs}">
                    <c:if test="${func.children.size()>0}">
                        <li class="open">
                        <a href="${func.url}" class="dropdown-toggle">

                            <i class="${func.icon}"></i>

                            <span class="menu-text">${func.name}</span>

                            <b class="arrow icon-angle-down"></b>

                        </a>
                    </c:if>
                    <c:if test="${func.children.size()==0}">
                        <li <c:if test="${func.name==currentname}">class="active"</c:if> >
                        <a href="${func.url}">

                            <i class="${func.icon}"></i>

                            <span class="menu-text">${func.name}</span>

                        </a>
                    </c:if>

                    <c:if test="${func.children!=null}">
                        <ul class="submenu" style="display:block;">
                            <c:forEach var="item" items="${func.children}">
                                <li <c:if test="${item.name==currentname}">class="active"</c:if> >
                                    <a href="${item.url}">

                                        <i class="icon-double-angle-right"></i>
                                            ${item.name}

                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:if>
                    </li>
                </c:forEach>

            </ul><!-- /.nav-list -->


            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
            </div>
            <script type="text/javascript">

                try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}

            </script>

        </div>



        <div class="main-content">

            <div class="breadcrumbs" id="breadcrumbs">

                <script type="text/javascript">

                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}

                </script>



                <ul class="breadcrumb">

                    <li>

                        <i class="icon-home home-icon"></i>

                        <a href="#">首页</a>

                    </li>

                    <li class="active">我的账户</li>

                </ul><!-- .breadcrumb -->

            </div>



            <div class="page-content">

                <div class="page-header">

                    <h1>
                        ${currentname}
                    </h1>

                </div>


                <div class="hr hr-18 hr-double dotted"></div>

                <div class="row">

                    <div class="col-xs-12">

                        <!-- PAGE CONTENT BEGINS -->



                        <div class="row">
                            <div class="col-xs-12">

                                <form class="form-horizontal" action="/revisePassword" id="ppp" name="ppp" method="post">

                                    <div class="form-group">

                                        <label class="col-sm-3 control-label no-padding-right">旧密码</label>
                                        <input type="text" style="display: none;" name="userID" value="${student_id}">


                                        <div class="col-sm-9">

                                            <input type="password" name="oldpassword" placeholder="旧密码" class="col-xs-10 col-sm-6" required/>

                                        </div>

                                    </div>
                                    <div class="form-group">

                                        <label class="col-sm-3 control-label no-padding-right">新密码</label>



                                        <div class="col-sm-9">

                                            <input type="password" placeholder="新密码" name="password" class="col-xs-10 col-sm-6" required/>

                                        </div>

                                    </div>
                                    <div class="form-group">

                                        <label class="col-sm-3 control-label no-padding-right">确认密码</label>




                                        <div class="col-sm-9">

                                            <input type="password" placeholder="确认密码" name="password_"  class="col-xs-10 col-sm-6" required/><div id="msg" style="color:red;text-shadow: 1px 1px 1px lightgray"></div>

                                        </div>

                                    </div>
                                    <div class="form-group">


                                        <label class="col-sm-3 control-label no-padding-right"></label>
                                        <div class="col-sm-9">

                                            <input type="button" onclick="checkpwd()" class="btn btn-xs btn-info col-xs-5" value="提交" />

                                        </div>

                                    </div>


                                    <div class="space-4"></div>

                                </form>
                            </div>
                        </div>
                    </div>
                </div>



            </div><!-- /.main-content -->



            <div class="ace-settings-container" id="ace-settings-container">

                <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">

                    <i class="icon-cog bigger-150"></i>

                </div>



                <div class="ace-settings-box" id="ace-settings-box">

                    <div>

                        <div class="pull-left">

                            <select id="skin-colorpicker" class="hide">

                                <option data-skin="default" value="#438EB9">#438EB9</option>

                                <option data-skin="skin-1" value="#222A2D">#222A2D</option>

                                <option data-skin="skin-2" value="#C6487E">#C6487E</option>

                                <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>

                            </select>

                        </div>

                        <span>&nbsp; 选择皮肤</span>

                    </div>



                    <div>

                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-navbar" />

                        <label class="lbl" for="ace-settings-navbar"> 固定导航条</label>

                    </div>



                    <div>

                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-sidebar" />

                        <label class="lbl" for="ace-settings-sidebar"> 固定滑动条</label>

                    </div>



                    <div>

                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-breadcrumbs" />

                        <label class="lbl" for="ace-settings-breadcrumbs">固定面包屑</label>

                    </div>



                    <div>

                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" />

                        <label class="lbl" for="ace-settings-rtl">切换到左边</label>

                    </div>



                    <div>

                        <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container" />

                        <label class="lbl" for="ace-settings-add-container">

                            切换窄屏

                            <b></b>

                        </label>

                    </div>

                </div>

            </div><!-- /#ace-settings-container -->

        </div><!-- /.main-container-inner -->



        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">

            <i class="icon-double-angle-up icon-only bigger-110"></i>

        </a>

    </div><!-- /.main-container -->


    <script src="../assets/js/jquery-2.1.1.js"></script>

    <script type="text/javascript">

        window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");

    </script>

    <script type="text/javascript">
        window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
    </script>
    <script type="text/javascript">

        if("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");

    </script>
    <script src="assets/js/bootstrap-3.3.4.js"></script>
    <script src="assets/js/typeahead-bs2.min.js"></script>
    <script src="assets/js/excanvas.min.js"></script>
    <script src="assets/js/jquery-ui-1.10.3.custom.min.js"></script>
    <script src="assets/js/jquery.ui.touch-punch.min.js"></script>
    <script src="assets/js/jquery.slimscroll.min.js"></script>
    <script src="assets/js/ace-elements.min.js"></script>
    <script src="assets/js/ace.min.js"></script>



    <!-- inline scripts related to this page -->



    <script type="text/javascript">
        function checkpwd(){
            var p1=document.ppp.password.value;//获取密码框的值
            var p2=document.ppp.password_.value;//获取重新输入的密码值
            if(p1==""){
                alert("请输入密码！");//检测到密码为空，提醒输入//

                return false;//退出检测函数
            }//如果允许空密码，可取消这个条件

            if(p1!=p2){//判断两次输入的值是否一致，不一致则显示错误信息
                document.getElementById("msg").innerHTML="两次输入密码不一致，请重新输入";//在div显示错误信息
                return false;
            }else{
                //密码一致，可以继续下一步操作
                document.getElementById("ppp").submit();
            }
        }

        jQuery(function($) {

            $('.easy-pie-chart.percentage').each(function(){

                var $box = $(this).closest('.infobox');

                var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');

                var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';

                var size = parseInt($(this).data('size')) || 50;

                $(this).easyPieChart({

                    barColor: barColor,

                    trackColor: trackColor,

                    scaleColor: false,

                    lineCap: 'butt',

                    lineWidth: parseInt(size/10),

                    animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,

                    size: size

                });

            })



            $('.sparkline').each(function(){

                var $box = $(this).closest('.infobox');

                var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';

                $(this).sparkline('html', {tagValuesAttribute:'data-values', type: 'bar', barColor: barColor , chartRangeMin:$(this).data('min') || 0} );

            });









            var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'150px'});

            var data = [

                { label: "social networks",  data: 38.7, color: "#68BC31"},

                { label: "search engines",  data: 24.5, color: "#2091CF"},

                { label: "ad campaigns",  data: 8.2, color: "#AF4E96"},

                { label: "direct traffic",  data: 18.6, color: "#DA5430"},

                { label: "other",  data: 10, color: "#FEE074"}

            ]



            var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');

            var previousPoint = null;



            placeholder.on('plothover', function (event, pos, item) {

                if(item) {

                    if (previousPoint != item.seriesIndex) {

                        previousPoint = item.seriesIndex;

                        var tip = item.series['label'] + " : " + item.series['percent']+'%';

                        $tooltip.show().children(0).text(tip);

                    }

                    $tooltip.css({top:pos.pageY + 10, left:pos.pageX + 10});

                } else {

                    $tooltip.hide();

                    previousPoint = null;

                }



            });













            var d1 = [];

            for (var i = 0; i < Math.PI * 2; i += 0.5) {

                d1.push([i, Math.sin(i)]);

            }



            var d2 = [];

            for (var i = 0; i < Math.PI * 2; i += 0.5) {

                d2.push([i, Math.cos(i)]);

            }



            var d3 = [];

            for (var i = 0; i < Math.PI * 2; i += 0.2) {

                d3.push([i, Math.tan(i)]);

            }





            var sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});






            $('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});

            function tooltip_placement(context, source) {

                var $source = $(source);

                var $parent = $source.closest('.tab-content')

                var off1 = $parent.offset();

                var w1 = $parent.width();



                var off2 = $source.offset();

                var w2 = $source.width();



                if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';

                return 'left';

            }





            $('.dialogs,.comments').slimScroll({

                height: '300px'

            });





            //Android's default browser somehow is confused when tapping on label which will lead to dragging the task

            //so disable dragging when clicking on label

            var agent = navigator.userAgent.toLowerCase();

            if("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))

                $('#tasks').on('touchstart', function(e){

                    var li = $(e.target).closest('#tasks li');

                    if(li.length == 0)return;

                    var label = li.find('label.inline').get(0);

                    if(label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation() ;

                });



            $('#tasks').sortable({
                    opacity:0.8,

                    revert:true,

                    forceHelperSize:true,

                    placeholder: 'draggable-placeholder',

                    forcePlaceholderSize:true,

                    tolerance:'pointer',

                    stop: function( event, ui ) {//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved

                        $(ui.item).css('z-index', 'auto');

                    }

                }

            );

            $('#tasks').disableSelection();

            $('#tasks input:checkbox').removeAttr('checked').on('click', function(){

                if(this.checked) $(this).closest('li').addClass('selected');

                else $(this).closest('li').removeClass('selected');

            });





        })

    </script>



</body>

</html>



