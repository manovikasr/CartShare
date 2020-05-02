import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
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
            total: 0
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

        // this.setState({
        //     cart_items: [{
        //         id: 1,
        //         product_name: "A",
        //         sku: "123",
        //         quantity: 2,
        //         price: 20
        //     },
        //     {
        //         id: 2,
        //         product_name: "B",
        //         sku: "XYZ",
        //         quantity: 4,
        //         price: 50
        //     }]
        // })

        this.calculateTotals();
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

    calculateTotals = () => {
        const cart_items = this.state.cart_items;
        var subTotal = 0, taxAmount, total;
        cart_items.forEach(item => {
            subTotal += (item.quantity * item.price);
        });

        taxAmount = (subTotal * this.state.taxPercent / 100).toFixed(2);
        total = (subTotal + subTotal * this.state.taxPercent / 100).toFixed(2);
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
        await this.setState({
            cart_items
        });
        localStorage.setItem("cart_items", JSON.stringify(cart_items));
        this.calculateTotals();
    };

    confirmOrder = (e) => {

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
                            <input type="number" name={item.id} min="1" max="10" width="10%" onChange={this.onQuantityChange} defaultValue={item.quantity}></input>
                        </td>
                        <td align="center">
                            <i class="fas fa-trash-alt" name={item.id} onClick={this.removeItem} alt="" />
                        </td>
                        <td align="center">$ {item.price * item.quantity}</td>
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
                            <td colSpan="4">Service Fees ({this.state.taxPercent}%)</td><td></td>
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