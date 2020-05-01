import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button, Card } from "react-bootstrap";
import PropTypes from "prop-types";
import OrderProductsModal from "./OrderProductsModal";
import { connect } from "react-redux";
import OrderCard from "./OrdersCard";

class PickUpOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pending_orders:[{
                order_id:1,
                no_products:5,
                status:"Placed"
            },{
                order_id:2,
                no_products:3,
                status:"Placed"
            },{
                order_id:3,
                no_products:2,
                status:"Placed"
            },{
                order_id:4,
                no_products:4,
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
    }

    handleToggle = () => {
        this.setState({
            showModal:!this.state.showModal
        });
    }

    hideModal = () => {
        this.setState({
            showModal:false
        });
    }

    render() {
        const { user } = this.props.auth;
        var orders_list, ordersTable, orders, actionButtons;
        if(this.state.pending_orders.length) {
            orders_list = this.state.pending_orders.map(order => {
                return (
                    <Col sm={3} onClick = {this.handleToggle}>
                        <OrderCard order = {order}/>
                    </Col>
                ) 
            });
            actionButtons = (
                <div>
                    <Button variant="success">Confirm</Button> &nbsp;&nbsp;&nbsp;
                    <Button variant="danger" onClick = {this.clearDetails}>Clear</Button> 
                </div>
            );
        }
        else {
            orders = (
                <div className = "py-4">
                    <Alert variant="info">You don't have any orders yet.</Alert>
                </div>
            );   
        }


        return (
            <div className = "container" style = {{ width: "75%"}}>
                <h2 className = "p-4">Orders to Deliver</h2>
                <Row>{orders_list}</Row> 
                <br/><br/>
                <div className = "mx-3">{actionButtons}</div>    
                <OrderProductsModal showModal = {this.state.showModal} onHide = {this.hideModal} />
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