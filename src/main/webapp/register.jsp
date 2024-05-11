<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="com.abhay.connection.*, com.abhay.model.*, com.abhay.dao.*, java.util.*"%>
<%
UserModel user = (UserModel) request.getSession().getAttribute("user");
if (user != null) {
	request.setAttribute("user", user);
}

ProductDao pd = new ProductDao(DBCon.getConnection());
List<Product> products = pd.getAllProducts();

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if(cart_list != null){
	request.setAttribute("cart_list", cart_list);
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Register Yourself</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
<%@include file="includes/navbar.jsp"%>

<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center">User Login</div>
			<div class="card-body">
				<form action="register-user" method="post">
				<div class="form-group">
						<label>Name : </label> <input type="text"
							class="form-control" name="name"
							placeholder="Enter Your Name" required />
					</div>
					<div class="form-group">
						<label>Email Address : </label> <input type="email"
							class="form-control" name="login-email"
							placeholder="Enter Registerd Email" required />
					</div>
					<div class="form-group">
						<label>Password : </label> <input type="password"
							class="form-control" name="login-password"
							placeholder="**********" required />
					</div>
					<div class="text-center mt-3">
						<button type="submit" class="btn btn-primary">Signup</button>
					</div>
				</form>
			</div>
		</div>
	</div>


<%@include file="includes/footer.jsp"%>
</body>
</html>