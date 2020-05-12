<html>
<head></head>

<body>
	<p>Hi ${deliverer},</p>
	<p>${no_of_orders} are to be delivered. Details of such orders are as follows: </p>
	
	<#list orders as order>
		<b>Order Id : ${order.id}</b>
		<table border="1">
			<tr>
				<th>Product Id</th>
				<th>Product Name</th>
				<th>SKU</th>
				<th>Product Price</th>
				<th>Total Price</th>
				<th>Quantity</th>
				<th>Product Description</th>
				<th>Product Brand</th>
				<th>Unit Type</th>
			
			</tr>
		
			
			<#list order.order_details as order_detail>
				<tr>
					<td><center>${order_detail.product_id}</center></td>
					<td><center>${order_detail.product_name}</center></td>
					<td><center>${order_detail.sku}</center></td>
					<td><center>${order_detail.product_price}</center></td>
					<td><center>${order_detail.total_price}</center></td>
					<td><center>${order_detail.quantity}</center></td>
					<td><center>${order_detail.product_desc}</center></td>
					<td><center>${order_detail.product_brand}</center></td>
					<td><center>${order_detail.unit_type}</center></td>
				</tr>
			</#list>
		</table>
	</#list>
	<p>Thanks,</p>
	<p>Cart Share Team</p>	
</body>
</html>