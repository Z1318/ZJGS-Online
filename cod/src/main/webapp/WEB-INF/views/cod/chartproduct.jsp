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

                    <li class="active">报表栏目</li>

                </ul><!-- .breadcrumb -->

            </div>



            <div class="page-content">

                <div class="page-header">

                    <h1>
                        ${currentname}
                    </h1>

                </div>
                <div style="height:50px;">
                    <form id="signupListImportForm" enctype="multipart/form-data">
                        <input class="btn btn-primary btn-xs" type="button"  value="导入数据" onclick="selectFile('#excelFile')" style="float: left;"/>
                        <input class="col-xs-3" id="excelFile" type="file" name="filename" multiple="multiple" onchange="fileUpload()" style="display: none;"/>
                    </form>
                    <div class="col-xs-1"></div>
                    <form action="/chartproduct" method="get">
                        <input type="text" name="sTime" required="true" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${newtime}" style="float: left;">
                        <input class="btn btn-primary btn-xs" type="submit" value="查询" style="float: left;height: 28px;">
                    </form>
                </div>
                <div class="hr hr-18 hr-double dotted"></div>

                <div class="row">

                    <div class="col-xs-12">

                        <div class="table-responsive">

                            <table id="sample-table-1" class="table table-striped table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>所属组</th>
                                    <th>产品名称</th>
                                    <th>产品sku</th>
                                    <th>投放人</th>
                                    <th>facebook支出</th>
                                    <th>facebook收入</th>
                                    <th>订单数</th>
                                    <th>加车次数</th>
                                    <th>展示次数</th>
                                    <th>网站转化</th>
                                    <th>单次成效</th>
                                    <th>单次点击费用</th>
                                    <th>投放时间</th>
                                    <th>导入时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${chartproducts}">
                                    <tr>
                                        <td>${item.team}</td>
                                        <td class="">${item.name}</td>
                                        <td>${item.sku}</td>
                                        <td class="">${item.person}</td>
                                        <td>${item.fb_cost}</td>
                                        <td class="">${item.fb_income}</td>
                                        <td >${item.order_num}</td>
                                        <td class="">${item.cart_num}</td>
                                        <td >${item.show_num}</td>
                                        <td class="">${item.site_conversion}</td>
                                        <td >${item.singleResult}</td>
                                        <td class="">${item.singleFree}</td>
                                        <td >${item.launch_time}</td>
                                        <td class="">${item.import_time}</td>
                                        <td>
                                            <div class="visible-md visible-lg hidden-sm hidden-xs btn-group">
                                                <button class="btn btn-primary btn-sm rolebutton" data-toggle="popover-x" data-target="#myPopover20a"
                                                        data-placement="left left-top"
                                                        data-id="${item.id}" data-team="${item.team}" data-name="${item.name}" data-sku="${item.sku}" data-person="${item.person}"
                                                        data-fb_cost="${item.fb_cost}" data-fb_income="${item.fb_income}" data-order_num="${item.order_num}" data-cart_num="${item.cart_num}"
                                                        data-show_num="${item.show_num}" data-site_conversion="${item.site_conversion}" data-singleResult="${item.singleResult}"
                                                        data-singleFree="${item.singleFree}" data-launch_time="${item.launch_time}" data-import_time="${item.import_time}">
                                                    修改
                                                </button>
                                            </div>
                                        </td>

                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>

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
<div id="dialog" class="animated" style="top: 20%;width: 30%;">
    <img class="dialogIco" width="50" height="50" src="#" alt="" />
    <div class="dialogTop">
        <a href="javascript:;" class="claseDialogBtn">关闭 <span style="color:red;font-size: 14px">×</span></a>
    </div>
    <form action="/revise" method="post" id="editForm">
            <table style="margin: 0 auto; border-collapse:separate; border-spacing:0px 10px;">
                <tr>
                    <label><td style="font-size: 13px;" ><font color="#ff0000">* </font>所属组</td>
                               <td><input type="text" name="team" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>产品名称</td>
                        <td><input type="text" name="name" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>产品sku</td>
                        <td><input type="text" name="sku" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>投放人</td>
                        <td><input type="text" name="person" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>facebook支出</td>
                        <td><input type="text" name="fb_cost" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>facebook收入</td>
                        <td><input type="text" name="fb_income" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>订单数</td>
                        <td><input type="text" name="order_num" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>加车次数</td>
                        <td><input type="text" name="cart_num" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>展示次数</td>
                        <td><input type="text" name="show_num" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>网站转化</td>
                        <td><input type="text" name="site_conversion" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>单次成效</td>
                        <td><input type="text" name="singleResult" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>单次点击费用</td>
                        <td><input type="text" name="singleFree" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <label><td style="font-size: 13px;"><font color="#ff0000">* </font>投放时间</td>
                        <td><input type="text" name="launch_time" required value="" class="ipt" /></td>
                    </label>
                </tr>
                <tr>
                    <td></td>
                    <td ><input type="submit" value="添加" class="submitBtn" /></td>
                </tr>
            </table>

        <input name="id" type="hidden">

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
<script src="../assets/js/jquery-form.js"></script>
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
<script>
    //触发选择文件事件
    function selectFile(select){
        $(select).trigger("click");
    }
    //文件上传
    function fileUpload(){
        var option = {
            url : "/channelchartProduct",//这里写你的url
            type : 'POST',
            clearForm: true,//提交后是否清空
            success : function(data) {
                setTimeout(function () {
                    alert(data);
                    window.location.href="/chartproduct";
                    }, 2000);
            } ,
            error:function(data){
                alert(data);
            }
        };
        $("#signupListImportForm").ajaxSubmit(option);
        return false;
    }
    $('.claseDialogBtn').click(function(){
        $('#dialogBg').fadeOut(300,function(){
            $('#dialog').addClass('bounceOutUp').fadeOut();
        });
    });


    $('.rolebutton').click(function () {
        $("#editForm").attr("action","/reviseproduct");
        $("input[name='id']").val($(this).attr("data-id"));
        $("input[name='team']").val($(this).attr("data-team"));
        $("input[name='name']").val($(this).attr("data-name"));
        $("input[name='sku']").val($(this).attr("data-sku"));
        $("input[name='person']").val($(this).attr("data-person"));
        $("input[name='fb_cost']").val($(this).attr("data-fb_cost"));
        $("input[name='fb_income']").val($(this).attr("data-fb_income"));
        $("input[name='order_num']").val($(this).attr("data-order_num"));
        $("input[name='cart_num']").val($(this).attr("data-cart_num"));
        $("input[name='show_num']").val($(this).attr("data-show_num"));
        $("input[name='site_conversion']").val($(this).attr("data-site_conversion"));
        $("input[name='singleResult']").val($(this).attr("data-singleResult"));
        $("input[name='singleFree']").val($(this).attr("data-singleFree"));
        $("input[name='launch_time']").val($(this).attr("data-launch_time"));
        $(".submitBtn").val("修改");
        $('#dialogBg').fadeIn(300);
        $('#dialog').removeAttr('class').addClass('animated '+'bounceIn'+'').fadeIn();
    });
</script>
</body>

</html>



