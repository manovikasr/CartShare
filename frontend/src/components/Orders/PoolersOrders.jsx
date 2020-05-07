import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import { Row, Col, Alert, Table, Form, Button, Card } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import OrderProductsModal from "./OrderProductsModal";
import axios from "axios";
import storeImage from "../../images/StoreImage.png";

class PoolersOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            store: {},
            order_count: 0,
            pending_orders: [{
                order_id: 1,
                no_products: 5,
                store_name: "abc",
                status: "Placed"
            }]
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated || this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
        if (this.props.location.state) {
            const store = this.props.location.state.store;
            this.setState({ store });
            this.getPoolersOrders(store.id);
        } else {
            //this.props.history.push("/orders/myorders");
        }
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    }

    clearForm = () => {
        this.setState({
            order_count: 0
        });
    }

    getPoolersOrders = store_id => {
        axios.get(`/order/available/${store_id}`)
            .then(res => {
                if (res.data) {
                    console.log(res.data);
                    this.setState({
                        pending_orders: res.data.orders
                    });
                }
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response.data);
                }
            });
    }

    onSubmit = e => {
        e.preventDefault();


    }

    render() {
        var store, store_name, address, address2;
        var ordersTable, orders_list, form, status, store;

        if (this.state.store.id) {
            store_name = this.state.store.store_name;
            address = this.state.store.address;
            address2 = this.state.store.city + ", " + this.state.store.state + " - " + this.state.store.zip;
            store = (
                <Card bg="info" text="white" style={{ width: "70rem", height: "15rem", margin: "4%" }}>
                    <Row>
                        <Col>
                            <Card.Img style={{ width: "18rem", height: "15rem" }} src={storeImage} />
                        </Col>
                        <Card.Body>
                            <Card.Title><h1>{store_name}</h1></Card.Title>
                            <br />
                            <Card.Text>
                                <h4>
                                    {address}<br />
                                    {address2}
                                </h4>
                            </Card.Text>
                        </Card.Body>
                    </Row>
                </Card>
            );
        }

        if (this.state.pending_orders.length) {
            orders_list = this.state.pending_orders.map(order => {
                return (
                    <tr onClick={this.handleToggle}>
                        <td align="center">{order.order_id}</td>
                        <td align="center">{order.products}</td>
                        <td align="center">{order.order_date}</td>
                        <td align="center">{order.status}</td>
                    </tr>
                )
            });

            ordersTable = (
                <Table borderless striped style={{ width: "50%" }}>
                    <thead align="center">
                        <th>Order ID</th>
                        <th>No of Products</th>
                        <th>Order Date</th>
                        <th>Status</th>
                    </thead>
                    <tbody>
                        {orders_list}
                    </tbody>
                </Table>
            );

            form = (
                <Form onSubmit={this.onSubmit} autoComplete="off">
                    <Row>
                        <Col>
                            <Form.Label><h3>Select the number of orders you can pick for your pool members:</h3></Form.Label>
                        </Col>
                        <Col>
                            <Form.Control name="order_count"
                                type="number"
                                value={this.state.order_count}
                                onChange={this.onChange}
                                min="0"
                                max="10"
                                autoFocus
                                size="text"
                            />
                        </Col>
                        <Col>
                            <Button variant="success" type="submit">Confirm</Button> &nbsp;&nbsp;&nbsp;
                            <Button variant="warning" onClick={this.clearForm}>Clear</Button>
                        </Col>
                    </Row>
                </Form>
            );
        }
        else {
            ordersTable = (
                <div className="py-4">
                    <Alert variant="info">There are no orders of your pool members to pickup from this store.</Alert>
                </div>
            );
        }

        return (
            <div>
                <div className="container p-4">
                    <div className="justify-content-center align-items-center h-100">
                        {store}
                        <br />
                        {form}
                        <br /><br />
                        <center>
                            <div className="col-md-5 bg-white p-3 border rounded">
                                <h3>Your Poolers Orders from this store</h3>
                            </div>
                            <br />
                            {ordersTable}
                        </center>
                    </div>
                </div>
                {/* <OrderProductsModal showModal={this.state.showModal} onHide={this.hideModal} /> */}
            </div>
        );
    }
}

PoolersOrders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolersOrders));