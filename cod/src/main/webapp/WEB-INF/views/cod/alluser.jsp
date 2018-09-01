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
                        <li <c:if test="${func.url==currenturl}">class="active"</c:if> >
                        <a href="${func.url}">

                            <i class="${func.icon}"></i>

                            <span class="menu-text">${func.name}</span>

                        </a>
                    </c:if>

                    <c:if test="${func.children!=null}">
                        <ul class="submenu" style="display:block;">
                            <c:forEach var="item" items="${func.children}">
                                <li <c:if test="${item.url==currenturl}">class="active"</c:if> >
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

                        <div class="table-responsive">

                            <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>所属组</th>
                                    <th>状态</th>
                                    <th>ip</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${users.rows}">
                                    <tr>
                                        <td>${item.username}</td>
                                        <td>${item.phone}</td>
                                        <td>${item.blgroup}</td>
                                        <td>${item.status}</td>
                                        <td>${item.ip}</td>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                                                <button class="btn btn-primary btn-sm rolebutton" data-toggle="popover-x" data-target="#myPopover20a"
                                                        data-placement="left left-top" data-id="${item.id}" data-roles="${item.roles}">
                                                    角色分配
                                                </button>
                                                <button class="btn btn-primary btn-sm datebutton" data-toggle="popover-x" data-target="#myPopover20a1"
                                                        data-placement="left left-top" data-id="${item.id}" data-group="${item.groupNames}">
                                                    数据分配
                                                </button>
                                            </div>
                                        </td>

                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                            <%--分页组件--%>
                            <div>${users.htmlst}</div>

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
</div>

<!--弹出框-->
<div id="dialogBg"></div>

<!--角色分配-->
<div id="myPopover20a" class="popover popover-default" style="    top: 35%;
    left: 45%;
    display: none;
    z-index: 999999999;
    padding-top: 0px;
    padding-bottom: 0px;">
    <div class="arrow"></div>
    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>请选择角色</h3>
    <form action="/addroleid" method="post">
        <div class="popover-content">
            <div class="control-group">
                <c:forEach var="item" items="${roles}">
                    <div>
                        <i class='input_style checkbox_bg' data-id="${item.id}"><input type="checkbox" name="rolesid" data-id="${item.id}" value="${item.id}"></i>
                            ${item.name}
                    </div>
                </c:forEach>
            </div>
        </div>
        <input type="hidden" name="user_id">
        <div class="popover-footer">
            <button type="submit" class="btn btn-sm btn-primary" style="margin-left:40px;text-align: center">提交</button>
        </div>
    </form>
</div>

<!--数据分配-->
<div id="myPopover20a1" class="popover popover-default" style="    top: 35%;
    left: 45%;
    display: none;
    z-index: 999999999;
    position: fixed;
    top:250px;
    padding-top: 0px;
    padding-bottom: 0px;">
    <div class="arrow"></div>
    <h3 class="popover-title"><span class="close pull-right" data-dismiss="popover-x">&times;</span>请选择数据</h3>
    <form action="/addgrouppower" method="post">
        <div class="popover-content">
            <div class="control-group">
                <c:forEach var="item" items="${groupNames}">
                    <div>
                        <i class='input_style checkbox_bg' data-id="${item.groupName}"><input type="checkbox" name="groupname" data-id="${item.groupName}" value="${item.groupName}"></i>
                            ${item.groupName}
                    </div>
                </c:forEach>
            </div>
        </div>
        <input type="hidden" name="user_id">
        <div class="popover-footer">
            <button type="submit" class="btn btn-sm btn-primary" style="margin-left:40px;text-align: center">提交</button>
        </div>
    </form>

</div>


<script type="text/javascript">

    window.jQuery || document.write("<script src='../assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");

</script>




<!--[if IE]>

<script type="text/javascript">

    window.jQuery || document.write("<script src='../assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");

</script>

<![endif]-->



<script type="text/javascript">

    if("ontouchend" in document) document.write("<script src='../assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");

</script>

<script src="../assets/js/bootstrap-3.3.4.js"></script>

<script src="../assets/js/typeahead-bs2.min.js"></script>



<!-- page specific plugin scripts -->



<!--[if lte IE 8]>

<script src="../assets/js/excanvas.min.js"></script>

<![endif]-->



<script src="../assets/js/jquery-ui-1.10.3.custom.min.js"></script>

<script src="../assets/js/jquery.ui.touch-punch.min.js"></script>

<script src="../assets/js/jquery.slimscroll.min.js"></script>

<script src="../assets/js/jquery.easy-pie-chart.min.js"></script>

<script src="../assets/js/jquery.sparkline.min.js"></script>

<!-- ace scripts -->
<script src="../assets/js/ace-elements.min.js"></script>

<script src="../assets/js/ace.min.js"></script>
<script src="../assets/js/input.js"></script>




<script type="text/javascript">

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

<script type="text/javascript">

    // 菜单管理按钮事件
    $(".close").click(function () {
        $('#dialogBg').fadeOut(300,function(){
            $('#myPopover20a').addClass('bounceOutUp').fadeOut();
        });
    });
    $(".rolebutton").click(function () {
        $("input[name='user_id']").val($(this).attr("data-id"));
        $('#dialogBg').fadeIn(300);
        $('#myPopover20a').fadeIn();
    })
    $(".rolebutton").click(function () {

        $(".input_style").each(function () {
            $(this).removeClass("checkbox_bg_check");
            $(this).find("input").attr("checked",false);
        });

        var roleid = $(this).attr("data-roles");
        var strs = new Array();
        strs = roleid.split(",");
        //遍历当前角色
        for (var i = 0; i < strs.length; i++) {
            //遍历所有角色
            $(".input_style").each(function () {
                if($(this).attr("data-id")==strs[i]){
                    $(this).addClass("checkbox_bg_check");
                    $(this).find("input").attr("checked",true);
                }
            });
        }
    })


    // 数据管理按钮事件
    $(".close").click(function () {
        $('#dialogBg').fadeOut(300,function(){
            $('#myPopover20a1').addClass('bounceOutUp').fadeOut();
        });
    });
    $(".datebutton").click(function () {
        $("input[name='user_id']").val($(this).attr("data-id"));
        $('#dialogBg').fadeIn(300);
        $('#myPopover20a1').fadeIn();
    })
    $(".datebutton").click(function () {

        $(".input_style").each(function () {
            $(this).removeClass("checkbox_bg_check");
            $(this).find("input").attr("checked",false);
        });

        var groups = $(this).attr("data-group");
        var strs = new Array();
        strs = groups.split(",");
        //遍历当前数据
        for (var i = 0; i < strs.length; i++) {
            //遍历所有数据
            $(".input_style").each(function () {
                if($(this).attr("data-id")==strs[i]){
                    $(this).addClass("checkbox_bg_check");
                    $(this).find("input").attr("checked",true);
                }
            });
        }
    })
</script>

</body>

</html>



