import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import ConfirmOrderModal from "./ConfirmOrderModal";
import { Alert, Button, Card, ListGroup, ListGroupItem, Table } from "react-bootstrap";

class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cart_items: [],
            store: null,
            taxPercent: 9.75,
            taxAmount: 0,
            subTotal: 0,
            total: 0,
            showModal: false,
            user: {}
        };
    }

    async componentDidMount() {
        if (!this.props.auth.isAuthenticated && this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
        if (localStorage.getItem("cart_store_id")) {
            this.getStoreDetails(localStorage.getItem("cart_store_id"));
        }
        if (localStorage.getItem("cart_items")) {
            const cart_items = JSON.parse(localStorage.getItem("cart_items"));
            await this.setState({
                cart_items
            });
        }

        this.calculateTotals();

        this.getProfile();
    }

    getStoreDetails = store_id => {

        axios.get(`store/${store_id}`)
            .then(res => {
                if (res.data) {
                    this.setState({
                        store: res.data.store
                    });
                }
            })
            .catch(e => {
                console.log(e);
            })
    };

    getProfile = () => {
        axios.get("/profile")
            .then(res => {
                if (res.data) {
                    this.setState({
                        user: res.data.user
                    });
                }
            })
            .catch(e => {
                console.log(e);
            });
    }

    calculateTotals = () => {
        const cart_items = this.state.cart_items;
        var subTotal = 0, taxAmount, total;
        cart_items.forEach(item => {
            subTotal += (item.quantity * item.price);
        });
;
        taxAmount = (subTotal * this.state.taxPercent / 100);
        total = subTotal + ((subTotal * this.state.taxPercent) / 100);
        subTotal = subTotal.toFixed(2);
        taxAmount = taxAmount.toFixed(2);
        total = total.toFixed(2);
        this.setState({
            subTotal, taxAmount, total
        });
    }

    onQuantityChange = async (e) => {
        let product_id = parseInt(e.target.name);
        let newQuantity = parseInt(e.target.value);
        let cart_items = this.state.cart_items;
        let index = cart_items.findIndex((item => item.id === product_id));
        cart_items[index].quantity = newQuantity;
        cart_items[index].total_price = (cart_items[index].price * newQuantity).toFixed(2);
        await this.setState({
            cart_items
        });
        localStorage.setItem("cart_items", JSON.stringify(cart_items));
        this.calculateTotals();
    };

    confirmOrder = (e) => {
        this.setState({
            showModal: true
        });
    };

    onHideModal = (e) => {
        this.setState({
            showModal: false
        });
    }

    placeOrder = (delivery) => {
        var order_items = JSON.parse(localStorage.getItem('cart_items'));
        order_items = order_items.map(item => {
            item.product_id = item.id;
            return item;
        });
        var pickup_type = delivery ? "other" : "self";
        var store_id = this.state.store.id;
        const orderData = {
            store_id,
            order_details: order_items,
            type_of_pickup: pickup_type
        }

        axios.post("/order", orderData)
            .then(res => {
                if (res.status === 200) {
                    this.clearCart();
                    this.onHideModal();
                    if (delivery)
                        this.props.history.push('/orders/myorders');
                    else
                        this.props.history.push({
                            pathname: '/orders/poolorders',
                            state: { store_id: store_id }
                        });
                }
            })
            .catch(e => {
                if (e.response && e.response.data)
                    this.setState({
                        error_message: e.response.data.message
                    });
            });
    };

    removeItem = async (e) => {
        const item_id = e.target.name;
        const cart_items = this.state.cart_items;
        var index = cart_items.findIndex(item => item.id === item_id);
        cart_items.splice(index, 1);
        await this.setState({
            cart_items
        });
        localStorage.setItem("cart_items", JSON.stringify(cart_items));
        this.calculateTotals();
    };

    clearCart = (e) => {
        localStorage.setItem("cart_store_id", "");
        localStorage.setItem("cart_items", JSON.stringify([]));

        this.setState({
            store: null,
            cart_items: [],
            subTotal: 0,
            total: 0
        });
    };

    render() {
        var cart, store, cartTable, productsList, actionButtons;
        const user = this.state.user;
        if (this.state.store && this.state.cart_items.length) {
            store = (
                <center>
                    <Card style={{ width: "60rem", margin: "2%" }}>
                        <ListGroup className="list-group-flush">
                            <ListGroupItem><h3>{this.state.store.store_name}</h3></ListGroupItem>
                            <ListGroupItem>
                                {this.state.store.address}<br />
                                {this.state.store.city + ", " + this.state.store.state + " - " + this.state.store.zip}<br />
                            </ListGroupItem>
                        </ListGroup>
                    </Card>
                </center>
            );
        }
        if (this.state.cart_items.length) {
            productsList = this.state.cart_items.map(item => {
                return (
                    <tr>
                        <td align="center">{item.product_name}</td>
                        <td align="center">{item.sku}</td>
                        <td align="center">$ {item.price}</td>
                        <td align="center">
                            <input type="number" name={item.id} min="1" max="10" width="10%" onChange={this.onQuantityChange} defaultValue={item.quantity}></input> {item.unit_type}
                        </td>
                        <td align="center">
                            <i class="fas fa-trash-alt" name={item.id} onClick={this.removeItem} alt="" />
                        </td>
                        <td align="center">$ {(item.price * item.quantity).toFixed(2)}</td>
                    </tr>
                );
            });
            cartTable = (
                <Table style={{ width: "100%" }}>
                    <thead align="center">
                        <th>Product Name</th>
                        <th>Product SKU</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th></th>
                        <th>Total Price</th>
                    </thead>
                    <tbody>
                        {productsList}
                        <br /><br /><br /><br />
                        <tr>
                            <td colSpan="4"><b>Sub Total</b></td><td></td>
                            <td align="center"><b>$ {this.state.subTotal}</b></td>
                        </tr>
                        <tr>
                            <td colSpan="4">Tax & Fees ({this.state.taxPercent}%)</td><td></td>
                            <td align="center">$ {this.state.taxAmount}</td>
                        </tr>
                        <tr>
                            <td colSpan="4"><b>Total</b></td><td></td>
                            <td align="center"><b>$ {this.state.total}</b></td>
                        </tr>
                    </tbody>
                </Table>
            );
            actionButtons = (
                <div>
                    <center>
                        <Button variant="danger" onClick={this.clearCart}>Clear Cart</Button> &nbsp;&nbsp;&nbsp;
                        <Button variant="warning" href="/home">Save For Later</Button> &nbsp;&nbsp;&nbsp;
                        <Button variant="success" onClick={this.confirmOrder}>Confirm Order</Button>
                    </center>
                </div>
            );
            cart = (
                <div>
                    {store}
                    {cartTable}
                    {actionButtons}
                </div>
            );
        } else {
            cart = (
                <div>
                    <Alert variant="info">You have not added any products into your cart yet.</Alert>
                    <center>
                        <Button variant="warning" href="/stores">Start Shopping</Button>
                    </center>
                </div>
            )
        }

        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <br />
                <h2>Your Cart</h2>
                {cart}
                <ConfirmOrderModal showModal={this.state.showModal} onHide={this.onHideModal} credits={user.contribution_credits} placeOrder={this.placeOrder}
                />
            </div>
        );
    }
}

Cart.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Cart));