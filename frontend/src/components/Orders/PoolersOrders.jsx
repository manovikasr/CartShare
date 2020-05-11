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
            clickedOrder: {},
            pending_orders: []
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
            const store_id = this.props.location.state.store_id;
            this.setState({ store_id });
            this.getStore(store_id);
            this.getPoolersOrders(store_id);
        } else {
            //this.props.history.push("/orders/myorders");
        }
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    clickOrder = (e) => {
        const order_id = parseInt(e.target.id);
        const orders = this.state.pending_orders;
        var clickedOrder = orders.find(order => order.id === order_id);
        this.setState({
            clickedOrder,
            showModal: true
        })
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

    getStore = store_id => {
        axios.get(`/store/${store_id}`)
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

    getPoolersOrders = store_id => {
        axios.get(`/order/available/${store_id}`)
            .then(res => {
                if (res.data) {
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

        const store_id = this.state.store.id;
        const order_count = this.state.order_count;

        axios.post(`/order/assign/${store_id}/${order_count}`)
        .then(res => {
            if(res.status === 200){
                this.props.history.push("/orders/pickup");
            }
        })
        .catch(e => {
            console.log(e);
        });



    }

    render() {
        var store, store_name, address, address2;
        var ordersTable, orders_list, form;

        if (this.state.store.id) {
            store_name = this.state.store.store_name;
            address = this.state.store.address;
            address2 = this.state.store.city + ", " + this.state.store.state + " - " + this.state.store.zip;
            store = (
                <Card bg="info" text="white" style={{ width: "70rem", height: "8rem", margin: "4%" }}>
                    <Row>
                        <Col sm={2}></Col>
                        <Col sm={3}>
                            <Card.Img style={{ width: "7rem", height: "8rem" }} src={storeImage} />
                        </Col>
                        <Card.Body>
                            <Card.Title><h1>{store_name}</h1></Card.Title>
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
                var count = 0;
                order.order_details.forEach(item => {
                    count += item.quantity;
                });
                return (
                    <tr id={order.id} onClick={this.clickOrder}>
                        <td align="center" id={order.id}>{order.id}</td>
                        <td align="center" id={order.id}>{count}</td>
                        <td align="center" id={order.id}>{(new Date(order.created_on)).toLocaleDateString("en-US")}</td>
                        <td align="center" id={order.id}>{order.status}</td>
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
                                max={Math.min(this.state.pending_orders.length, 10)}
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
                <OrderProductsModal order={this.state.clickedOrder} showModal={this.state.showModal} onHide={this.handleToggle} />
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