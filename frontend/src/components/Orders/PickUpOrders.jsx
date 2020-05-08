import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button, Card } from "react-bootstrap";
import PropTypes from "prop-types";
import axios from "axios";
import QRCodeModal from "./QRCodeModal";
import { connect } from "react-redux";
import OrderCard from "./OrderCard";
import storeImage from "../../images/StoreImage.png";

class PickUpOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pickup_orders:[],
            showModal: false
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
        this.getPickupOrders();
    }

    getPickupOrders = () => {
        axios.get("/order/pickup")
        .then(res => {
            if(res.data) {
                this.setState({
                    pickup_orders:res.data.orders
                });
            }
        })
        .catch(e => {
            if(e.response) {
                console.log(e.response.data);
            }
        });
    }

    handleToggle = () => {
        this.setState({
            showModal:!this.state.showModal
        });
    }

    render() {
        var order_cards, store, store_name;
        if(this.state.pickup_orders.length) {
            store_name = this.state.pickup_orders[0].store_name;
            store = (
                    <Card bg="info" text="white" style={{ width: "70rem", height: "8rem", margin: "4%" }}>
                        <Row>
                            <Col sm={2}></Col>
                            <Col sm={3}>
                                <Card.Img style={{ width: "7rem", height: "8rem" }} src={storeImage} />
                            </Col>
                            <Card.Body>
                                <Card.Title><h1>{store_name}</h1></Card.Title>
                            </Card.Body>
                            <Col>
                                <br/><br/>
                                <Button variant="primary" onClick={this.handleToggle}>Pickup All Orders</Button>
                            </Col>
                        </Row>
                    </Card>
            );
            order_cards = this.state.pickup_orders.map(order => {
                return (
                    <Col sm={3}>
                        <OrderCard order = {order}/>
                    </Col>
                );
            });
        }
        else {
            store = (
                <Alert variant="info">You don't have any orders to pickup</Alert>
            );   
        }


        return (
            <div className = "container" style = {{ width: "75%"}}>
                <h2 className="p-4">Pickup Orders</h2>
                {store}
                <Row>
                    {order_cards}
                </Row>
                <QRCodeModal orders={this.state.pickup_orders} showModal = {this.state.showModal} onHide = {this.handleToggle} />
            </div>
        );
    }
}

PickUpOrders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PickUpOrders));