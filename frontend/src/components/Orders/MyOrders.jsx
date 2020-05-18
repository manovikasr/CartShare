import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import { Row, Col, Alert } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import OrderCard from "./OrderCard";
import axios from "axios";

class MyOrders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            orders: []
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
        this.getMyOrders();
    }

    getMyOrders = () => {
        axios.get("/order/myorders")
            .then(res => {
                if (res.data.orders) {
                    this.setState({
                        orders: res.data.orders
                    });
                }
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response.data);
                }
            })
    }

    render() {
        var orders_list, message;
        if (this.state.orders.length) {
            orders_list = this.state.orders.map(order => {
                return (
                    <Col sm={3} key={order.id}>
                        <OrderCard order={order} getOrders={this.getMyOrders}/>
                    </Col>
                )
            });
        }
        else {
            message = (
                <div>
                    <Alert variant="info">You don't have any orders yet.</Alert>
                </div>
            );
        }


        return (
            <div className="container" style={{ width: "75%" }}>
                <h2 className="p-4">My Orders</h2>
                {message}
                <Row>{orders_list}</Row>
                <br /><br />
            </div>
        );
    }
}

MyOrders.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(MyOrders));