<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.abhay.model.*"%>
<%@ page import="com.abhay.dao.*"%>
<%@ page import="com.abhay.connection.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);
UserModel user = (UserModel) request.getSession().getAttribute("user");
if(user != null){
	request.setAttribute("user", user);
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

List<Cart> cartProduct = null;

if(cart_list != null){
	ProductDao pd = new ProductDao(DBCon.getConnection());
	cartProduct = pd.getCartProducts(cart_list);
	request.setAttribute("cart_list", cart_list);
	double total = pd.getTotalCartPrice(cartProduct);
	request.setAttribute("total", total);
}

%>
<!DOCTYPE html>
<html>
<head>
<title>Shoping Cart Cart</title>
<%@include file="includes/head.jsp"%>

<style type="text/css">
	.table tbody td {
		vertical-align: middle;
	}
	.btn-incre, .btn-decre{
		box-shadow: none;
		font-size: 25px;
	}
</style>

</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">	
		<div class="d-inline-flex py-3">
			<h3>Total Price: ${ (total>0) ? dcf.format(total):0 } Rs</h3>
			<a class="btn btn-primary mx-3" href="check-out">Check Out</a>
		</div>
		<table class="table table-ligth">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>`
			<%if(cart_list != null){
			for(Cart c : cartProduct){ %>
				<tr>
					<td><%= c.getName()%></td>
					<td><%= c.getCategory() %></td>
					<td><%= dcf.format(c.getPrice()) %> Rs</td>
					<td><form method="post" action="order-now" class="form-inline">
							<input type="hidden" name="id" value="<%= c.getId() %>" class="form-input" />
							<div class="form-group d-inline-flex justify-content-between w-50">
								<a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=<%= c.getId() %> "><i class="fas fa-minus-square"></i></a> 
								<input type="text" name="quantity" class="form-control w-50" value="<%= c.getQuantity() %>" readonly /> 
								<a class="btn btn-sm btn-incre" href="quantity-inc-dec?action=inc&id=<%= c.getId() %>"><i class="fas fa-plus-square"></i></a>
							</div>
							<button type="submit" class="btn btn-primary btn-sm">Buy</button>
						</form>
					</td>
					<td><a class="btn btn-sm btn-danger" href="remove-from-cart?id=<%= c.getId() %>">Remove</a></td>
				</tr>
				<% } }%>
			</tbody>
		</table>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>