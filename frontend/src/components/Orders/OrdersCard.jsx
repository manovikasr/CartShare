import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button } from "react-bootstrap";
import storeImage from "../../images/StoreImage.png";
import OrderProductsModal from "./OrderProductsModal";
import EditStatusModal from "./EditStatusModal";

class OrdersCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            showStatusModal: false,
            order:{}
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
            showModal:!this.state.showModal
        });
    }

    handleStatusModalToggle = () => {
        this.setState({
            showStatusModal:!this.state.showStatusModal
        });
    }

    hideModal = () => {
        this.setState({
            showModal:false
        });
    }

    hideStatusModal = () => {
        this.setState({
            showStatusModal:false
        });
    }

    render() {
        const { user } = this.props.auth;
        var actionButtons, store_name, editButton, order_date;
        var { created_on } = this.state.order;
        if(created_on){
            order_date = (
                <div>
                    Order Date - {created_on.substr(0,10)}
                </div>
            );
        }
        if(this.props.order.store_name) {
            store_name = (
                <div>
                    Store Name: {this.state.order.store_name}
                </div>
            )
        }
        if(this.props.order.user_id == user.id) {
            editButton = (
                <Button variant="info" size="sm"  onClick={this.handleStatusModalToggle}><b>Edit Status</b></Button>    
            )
        }
        return (
            <div>
            <Card bg="white" style={{ width: "18rem", margin: "5%" }}>
                <Card.Body>
                    <Card.Title><b>Order id - {this.state.order.id}</b></Card.Title>
                    <Card.Text>
                        {order_date}
                        {store_name}
                        Status: {this.state.order.status}<br />
                    </Card.Text>
                    {editButton}&nbsp;&nbsp;
                    <Button variant="success" size="sm" onClick={this.handleToggle}><b>View Products</b></Button>
                </Card.Body>
            </Card>
            <OrderProductsModal showModal = {this.state.showModal} onHide = {this.hideModal} order = {this.state.order}/> 
            <EditStatusModal showModal = {this.state.showStatusModal} onHide = {this.hideStatusModal} order = {this.state.order}/> 
            </div>
        );
    }
}

OrdersCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(OrdersCard));
