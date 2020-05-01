import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button } from "react-bootstrap";
import storeImage from "../../images/StoreImage.png";

class OrdersCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
    }

    componentDidMount() {

    }

    render() {
        const { user } = this.props.auth;
        var actionButtons, store_name;
        if(this.props.order.store_name) {
            store_name = (
                <div>
                    {this.props.order.store_name}
                </div>
            )
        }
        return (
            <Card bg="white" style={{ width: "18rem", margin: "5%" }}>
                <Card.Body>
                    <Card.Title><b>Order id - {this.props.order.order_id}</b></Card.Title>
                    <Card.Text>
                        No of Products: {this.props.order.no_products}<br />
                        {store_name}
                        Status: {this.props.order.status}<br />
                    </Card.Text>
                </Card.Body>
            </Card>
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
