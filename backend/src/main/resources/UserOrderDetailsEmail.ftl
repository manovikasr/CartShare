<html>
<head></head>

<body>
	<p>Hi ${deliverer},</p>
	<p>${no_of_orders} are to be delivered including yours. Details of such orders are as follows: </p>
	<table>
		<tr>
			<th>User Screen Name</th>
			<th>Address</th>
			<th>City</th>
			<th>Zip Code</th>
			<th>Order Id</th>
			<th>No of Products</th>
			<th>Order Time</th>
		</tr>
		<#list orders as order>
		<tr>
			<td><center>${order.user_id.screen_name}</center></td>
			<td><center>${order.user_id.address}</center></td>
			<td><center>${order.user_id.city}</center></td>
			<td><center>${order.user_id.zip}</center></td>
			<td><center>${order.order_id}</center></td>
			<td><center>${order.no_of_products}</center></td>
			<td><center>${order.order_time}</center></td>
		</tr>
		</#list>
	</table>
	<p>Thanks,</p>
	<p>Cart Share Team</p>	
</body>
</html>