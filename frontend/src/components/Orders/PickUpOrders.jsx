import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button, Card } from "react-bootstrap";
import PropTypes from "prop-types";
import QRCodeModal from "./QRCodeModal";
import { connect } from "react-redux";
import OrderCard from "./OrdersCard";
import axios from "axios";

class PickUpOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pending_orders:[],
            showQRcodeModal: false
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
        this.getOrderToDeliver();
    }

    getOrderToDeliver = () => {
         axios.get("/order/orders_for_delivery")
        .then(res => {
            if(res.data.orders) {
                this.setState({
                    pending_orders:res.data.orders
                });
            }
        })
        .catch(e => {
            if(e.response) {
                console.log(e.response.data);
            }
        })
    }

    handleQRcodeModalToggle = () => {
        this.setState({
            showQRcodeModal:!this.state.showQRcodeModal
        });
    }

    hideBarcodeModal = () => {
        this.setState({
            showQRcodeModal:false
        });
    }

    render() {
        const { user } = this.props.auth;
        var orders_list, ordersTable, orders, actionButtons;
        if(this.state.pending_orders && this.state.pending_orders.length) {
            orders_list = this.state.pending_orders.map(order => {
                return (
                    <Col sm={3}>
                        <OrderCard order = {order}/>
                    </Col>
                ) 
            });
            actionButtons = (
                <div>
                    <Button variant="success" onClick = {this.handleQRcodeModalToggle}>Confirm</Button> &nbsp;&nbsp;&nbsp;
                    <Button variant="danger" onClick = {this.clearDetails}>Clear</Button> 
                </div>
            );
        }
        else {
            orders_list = (
                <Alert variant="info">You don't have any orders to deliver yet.</Alert>
            );   
        }


        return (
            <div className = "container" style = {{ width: "75%"}}>
                <h2 className = "p-4">Orders to Deliver</h2>
                <Row>{orders_list}</Row> 
                <br/><br/>
                <div className = "mx-3">{actionButtons}</div>    
                <QRCodeModal showModal = {this.state.showQRcodeModal} onHide = {this.hideBarcodeModal} />
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