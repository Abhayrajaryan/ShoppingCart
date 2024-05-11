<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.abhay.model.*, java.util.*, com.abhay.dao.*, com.abhay.connection.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);

UserModel user = (UserModel) request.getSession().getAttribute("user");
List<Order> orderList = null;
if (user != null) {
	request.setAttribute("user", user);
	OrderDao od = new OrderDao(DBCon.getConnection());
	orderList = (List<Order>) od.userOrders(user.getId());
} else {
	response.sendRedirect("login.jsp");
}

ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");
if (cart_list != null) {
	request.setAttribute("cart_list", cart_list);
}


%>
<!DOCTYPE html>
<html>
<head>
<title>Shoping Cart Orders</title>
<%@include file="includes/head.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%if(orderList != null){
				for(Order o : orderList){ %>
				<tr>
					<td><%= o.getDate()%></td>
					<td><%= o.getName()%></td>
					<td><%= o.getCategory()%></td>
					<td><%= o.getQuantity()%></td>
					<td><%= dcf.format(o.getPrice())%> Rs.</td>
					<td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%= o.getOrderId()%>">Cancle</a></td>
				</tr>
				<%}}%>
			</tbody>
		</table>
	</div>

	<%@include file="includes/footer.jsp"%>
</body>
</html>