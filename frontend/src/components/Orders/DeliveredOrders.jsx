import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav, Container, Row, Col, Alert, Table, Form, Button } from "react-bootstrap";
import PropTypes from "prop-types";
import OrderProductsModal from "./OrderProductsModal";
import { connect } from "react-redux";
import OrderCard from "./OrdersCard";

class DeliveredOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            my_orders:[{
                order_id:1,
                no_products:5,
                store_name:"Store-1",
                status:"Placed",
                user_id:6
            },{
                order_id:2,
                no_products:3,
                store_name:"Store-2",
                status:"Placed",
                user_id:6
            },{
                order_id:3,
                no_products:2,
                store_name:"Store-3",
                status:"Placed",
                user_id:6
            },{
                order_id:4,
                no_products:4,
                store_name:"Store-4",
                status:"Placed",
                user_id:6
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
        console.log(this.props.auth.user);
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
        if(this.state.my_orders.length) {
            orders_list = this.state.my_orders.map(order => {
                return (
                    <Col sm={3} onClick = {this.handleToggle}>
                        <OrderCard order = {order}/>
                    </Col>
                ) 
            });
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
                <h2 className = "p-4">Delivered Orders</h2>
                <Row>{orders_list}</Row> 
                <br/><br/>  
                <OrderProductsModal showModal = {this.state.showModal} onHide = {this.hideModal} /> 
            </div>
        );
    }
}

DeliveredOrders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(DeliveredOrders));