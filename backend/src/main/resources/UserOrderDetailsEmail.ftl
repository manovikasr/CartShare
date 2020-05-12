<html>
<head></head>

<body>
	<p>Hi ${deliverer},</p>
	<p>${no_of_orders} order(s) to be delivered by you. Details of the orders to be delivered are as follows: </p>
	
	<#list orders as order>
		<h3><b>Order Id : ${order.id}</b><h3>
		<p><b>Name</b>: ${order.user.screen_name} </p>
		<p><b>Address</b>: ${order.user.address}, ${order.user.city}, ${order.user.state} - ${order.user.zip} </p>
		<table border="1">
			<tr>
				<th>SKU</th>
				<th>Product Name</th>
				<th>Product Brand</th>
				<th>Product Description</th>
				<th>Product Price</th>
				<th>Quantity</th>
				<th>Total Price</th>
			</tr>
		
			
			<#list order.order_details as order_detail>
				<tr>
					<td><center>${order_detail.sku}</center></td>
					<td><center>${order_detail.product_name}</center></td>
					<td><center>${order_detail.product_brand}</center></td>
					<td><center>${order_detail.product_desc}</center></td>
					<td><center>${order_detail.product_price}</center></td>
					<td><center>${order_detail.quantity} ${order_detail.unit_type}</center></td>
					<td><center>${order_detail.total_price}</center></td>
				</tr>
			</#list>
		</table>
	</#list>
	<p>Thanks,</p>
	<p>Cart Share Team</p>	
</body>
</html>