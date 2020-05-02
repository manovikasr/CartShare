import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button, Card } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import OrderProductsModal from "./OrderProductsModal";
import axios from "axios";
import storeImage from "../../images/StoreImage.png";

class PoolersOrderDelivery extends Component {
    constructor(props) {
        super(props);
        this.state = {
            //pending_orders:[],
            showModal:false,
            store:{},
            contribution_credits:0,
            status:"GREEN",
            no_of_orders_to_deliver:0,
            pending_orders:[{
                order_id:1,
                no_products:5,
                store_name:"abc",
                status:"Placed"
            },{
                order_id:2,
                no_products:3,
                store_name:"def",
                status:"Placed"
            },{
                order_id:3,
                no_products:2,
                store_name:"ghi",
                status:"Placed"
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
        this.getStoreInfo();
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    handleToggle = () => {
        this.setState({
            showModal:!this.state.showModal
        });
    }

    getStoreInfo = () => {
        axios.get("/store/"+"1")
        .then(res => {
            if (res.data) {
                this.setState({
                    store:res.data.store
                });
            }
        }).catch(e => {
            console.log(e);
        })
    }

    hideModal = () => {
        this.setState({
            showModal:false
        });
    }

    clearDetails = () => {
        this.setState({
            no_of_orders_to_deliver:0
        });
    }

    render() {
        const { user } = this.props.auth;
        var ordersTable, orders_list, orders, form, status, actionButtons, store;
        if(this.state.pending_orders.length) {
            orders_list = this.state.pending_orders.map(order => {
                return (
                    <tr onClick={this.handleToggle}>
                        <td align="center">{order.order_id}</td>
                        <td align="center">{order.no_products}</td>
                        <td align="center">{order.status}</td>
                    </tr>
                )
            });
            ordersTable = (
                <Table borderless striped>
                    <thead align="center">
                        <th>Order id</th>
                        <th>No of Products</th>
                        <th>Status</th>
                    </thead>
                    <tbody>
                        {orders_list}
                    </tbody>   
                </Table>
            );
            orders = (
                <div>
                    {ordersTable}
                </div>
            );

            actionButtons = (
                <div>
                    <center>
                        <Button variant="success">Confirm</Button> &nbsp;&nbsp;&nbsp;
                        <Button variant="danger" onClick = {this.clearDetails}>Clear</Button> 
                    </center>
                </div>
            );

            form = (
                <React.Fragment>
                <Form onSubmit={this.onSubmit} autoComplete="off">
                    <Form.Row>
                        <Form.Group controlId="no_of_orders_to_deliver">
                            <Form.Label><b>Select number of orders to deliver</b></Form.Label>
                                <Form.Control name="no_of_orders_to_deliver"
                                    type="number"
                                    defaultValue={0}
                                    onChange = {this.onChange}
                                    min="0"
                                    max="10"
                                    />
                        </Form.Group>
                        <Form.Group as={Col} controlId="buttons" className="p-4">
                            {actionButtons}
                        </Form.Group>  
                    </Form.Row>
                    <Form.Row >
                          
                    </Form.Row>
                </Form>
                </React.Fragment>
            );

            if(this.state.store) {
                store = (
                    <Card bg="white" style={{ width: "18rem", margin: "5%" }}>
                        <Card.Img
                            variant="top"
                            style={{ height: "15rem" }}
                            src={storeImage}
                        />
                    <Card.Body>
                        <center>
                            <Card.Title><b>{this.state.store.store_name}</b></Card.Title>
                        </center>
                        <Card.Text>
                            <b>Address: </b><br />
                            {this.state.store.address}<br />
                            {this.state.store.city}, {this.state.store.state} - {this.state.store.zip}
                        </Card.Text>
                    </Card.Body>
                </Card>
                )        
            }
            else {
                store = (
                    <div className = "py-4">
                        <Alert variant="danger">Store Information not found.</Alert>
                    </div>
                );      
            }
        }
        else {
            orders = (
                <div className = "py-4">
                    <Alert variant="info">You don't have any orders yet.</Alert>
                </div>
            );   
        }
        
        return (
            <div>
                <div className = "container p-4">
                    <div className = "row justify-content-center align-items-center h-100">
                        <div className = "col-md-6">
                            <div className = "px-2">{form}</div>
                            <div className="col-md-12 bg-white p-3 border rounded">
                                <center><b>All Orders are placed with - {this.state.store.store_name}</b></center>
                            </div>
                            <center>{store}</center>
                            <div className="col-md-12 bg-white p-3 border rounded">
                                <center><b>Pending Orders in the Pool</b></center>
                            </div>
                            {orders}
                        </div>
                    </div>
                </div>
                <OrderProductsModal showModal = {this.state.showModal} onHide = {this.hideModal} />
            </div>
        );
    }
}

PoolersOrderDelivery.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolersOrderDelivery));