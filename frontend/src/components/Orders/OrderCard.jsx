import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button } from "react-bootstrap";
import OrderProductsModal from "./OrderProductsModal";
import EditStatusModal from "./EditStatusModal";

class OrderCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            showStatusModal: false,
            order: {}
        };
    }

    componentDidMount() {
        console.log(this.props.order)
        this.setState({
            order: this.props.order
        });
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    }

    handleStatusToggle = () => {
        this.setState({
            showStatusModal: !this.state.showStatusModal
        });
    }

    render() {
        var updateButton;

        if (this.props.showAddress) {
            updateButton = (
                <Button variant="info" size="sm" onClick={this.handleStatusToggle}>Update Status</Button>
            );
        }
        return (
            <div>
                <Card bg="white" style={{ width: "18rem", margin: "5%" }}>
                    <Card.Body>
                        <Card.Title><b>Order #{this.state.order.id}</b></Card.Title>
                        <Card.Text>
                            <b>Store:</b> {this.props.order.store_name} <br />
                            <b>Order Date:</b> {(new Date(this.props.order.created_on)).toLocaleDateString("en-US")} <br />
                            <b>Status:</b> {this.props.order.status}<br />
                        </Card.Text>
                        {updateButton}&nbsp;&nbsp;
                    <Button variant="success" size="sm" onClick={this.handleToggle}><b>View Products</b></Button>
                    </Card.Body>
                </Card>
                <OrderProductsModal showModal={this.state.showModal} onHide={this.handleToggle} order={this.state.order} />
                <EditStatusModal showModal={this.state.showStatusModal} onHide={this.handleStatusToggle} order={this.state.order} getOrders={this.props.getOrders}/>
            </div>
        );
    }
}

OrderCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(OrderCard));
