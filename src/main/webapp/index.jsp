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
<title>Welcome To Shopping Cart</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	
	
	<div class="container">
		<div class="card-header my-3">All Products</div>
		<div class="row">
		<%
		if(!products.isEmpty()){
		for(Product p : products){ %>
			<div class="col-md-3 my-3">
				<div class="card w-100" style="width: 18rem;">
					<img class="card-img-top" src="product_images/<%= p.getImage() %>"
						alt="Card image cap">
					<div class="card-body">
						<h5 class="card-title"><%= p.getName() %></h5>
						<h5 class="price">Price: <%= p.getPrice() %> Rs.</h5>
						<h5 class="category">Category: <%= p.getCategory() %></h5>
						<div class="mt-3 d-flex justify-content-between">
							<a href="add-to-cart?id=<%= p.getId() %>" class="btn btn-dark">Add to Cart</a> 
							<a href="order-now?id=<%= p.getId() %>&&quantity=1" class="btn btn-primary">Buy Now</a>
						</div>
					</div>
				</div>
			</div>
			<% } }%>
		</div>
	</div>
	

	<%@include file="includes/footer.jsp"%>
</body>
</html>