<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>

<html lang="en">

	<head>

		<meta charset="utf-8" />
		<title>Cod报表系统</title>
		<meta name="keywords" content="Bootstrapģ��,Bootstrapģ������,Bootstrap�̳�,Bootstrap����" />
		<meta name="description" content="վ���ز��ṩBootstrapģ��,Bootstrap�̳�,Bootstrap���ķ�������Bootstrap�������" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath%>assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>assets/css/font-awesome.min.css">
		<link rel="stylesheet" href="<%=basePath%>assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath%>assets/css/ace-rtl.min.css" />
	</head>



	<body class="login-layout">

		<div class="main-container">

			<div class="main-content">

				<div class="row">

					<div class="col-sm-10 col-sm-offset-1">

						<div class="login-container">

							<div class="center">

								<h1>
									<span class="red">Cod报表系统</span>

								</h1>

							</div>



							<div class="space-6"></div>



							<div class="position-relative">

								<div id="login-box" class="login-box visible widget-box no-border">

									<div class="widget-body">

										<div class="widget-main">

											<h4 class="header blue lighter bigger">

												<i class="icon-coffee green"></i>

												请输入你的账户信息

											</h4>



											<div class="space-6"></div>



											<form method="post" action="/cod/login/dologin">

												<fieldset>

													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input type="text" name="username" class="form-control" placeholder="用户名" />

															<i class="icon-user"></i>

														</span>

													</label>



													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input name="password" type="password" class="form-control" placeholder="密码" />

															<i class="icon-lock"></i>

														</span>

													</label>



													<div class="space"></div>



													<div class="clearfix">

														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">

															<i class="icon-key"></i>

															登 陆

														</button>

													</div>



													<div class="space-4"></div>

												</fieldset>

											</form>

										</div>


									</div><!-- /widget-body -->

								</div><!-- /login-box -->



								<div id="forgot-box" class="forgot-box widget-box no-border">

									<div class="widget-body">

										<div class="widget-main">

											<h4 class="header red lighter bigger">

												<i class="icon-key"></i>

												Retrieve Password

											</h4>



											<div class="space-6"></div>

											<p>

												Enter your email and to receive instructions

											</p>



											<form>

												<fieldset>

													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input type="email" class="form-control" placeholder="Email" />

															<i class="icon-envelope"></i>

														</span>

													</label>



													<div class="clearfix">

														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">

															<i class="icon-lightbulb"></i>

															Send Me!

														</button>

													</div>

												</fieldset>

											</form>

										</div><!-- /widget-main -->



										<div class="toolbar center">

											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">

												Back to login

												<i class="icon-arrow-right"></i>

											</a>

										</div>

									</div><!-- /widget-body -->

								</div><!-- /forgot-box -->



								<div id="signup-box" class="signup-box widget-box no-border">

									<div class="widget-body">

										<div class="widget-main">

											<h4 class="header green lighter bigger">

												<i class="icon-group blue"></i>

												New User Registration

											</h4>



											<div class="space-6"></div>

											<p> Enter your details to begin: </p>



											<form>

												<fieldset>

													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input type="email" class="form-control" placeholder="Email" />

															<i class="icon-envelope"></i>

														</span>

													</label>



													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input type="text" class="form-control" placeholder="Username" />

															<i class="icon-user"></i>

														</span>

													</label>



													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input type="password" class="form-control" placeholder="Password" />

															<i class="icon-lock"></i>

														</span>

													</label>



													<label class="block clearfix">

														<span class="block input-icon input-icon-right">

															<input type="password" class="form-control" placeholder="Repeat password" />

															<i class="icon-retweet"></i>

														</span>

													</label>



													<label class="block">

														<input type="checkbox" class="ace" />

														<span class="lbl">

															I accept the

															<a href="#">User Agreement</a>

														</span>

													</label>



													<div class="space-24"></div>



													<div class="clearfix">

														<button type="reset" class="width-30 pull-left btn btn-sm">

															<i class="icon-refresh"></i>

															Reset

														</button>



														<button type="button" class="width-65 pull-right btn btn-sm btn-success">

															Register

															<i class="icon-arrow-right icon-on-right"></i>

														</button>

													</div>

												</fieldset>

											</form>

										</div>



										<div class="toolbar center">

											<a href="#" onclick="show_box('login-box'); return false;" class="back-to-login-link">

												<i class="icon-arrow-left"></i>

												Back to login

											</a>

										</div>

									</div><!-- /widget-body -->

								</div><!-- /signup-box -->

							</div><!-- /position-relative -->

						</div>

					</div><!-- /.col -->

				</div><!-- /.row -->

			</div>

		</div><!-- /.main-container -->

		<script src="../assets/js/jquery-2.1.1.js"></script>

		<script type="text/javascript">

			window.jQuery || document.write("<script src='../assets/js/jquery-2.0.3.min.js'>"+"<"+"/script>");

		</script>



		<script type="text/javascript">

			if("ontouchend" in document) document.write("<script src='../assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");

		</script>

		<script type="text/javascript">

			function show_box(id) {

			 jQuery('.widget-box.visible').removeClass('visible');

			 jQuery('#'+id).addClass('visible');

			}

		</script>

	

</body>

</html>

