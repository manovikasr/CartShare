import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button, Row, Col } from "react-bootstrap";

class PoolCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
    }

    componentDidMount() {

    }

    hideModal = () => {
        this.setState({
            showModal: false
        });
    };

    render() {
        const { user } = this.props.auth;
        const product = this.props.product;

        return (
            <Card bg="white" style={{ width: "50rem", margin: "2%" }}>
                <Row>
                    <Col>
                        <Card.Img style={{ width: "12rem", height: "12rem" }} alt="" src={product.product_img} />
                    </Col>
                        <Card.Body>
                            <Card.Title>{product.product_name}</Card.Title>
                            <Card.Text>
                                <b>Brand: </b>{product.product_brand}<br />
                                <b>SKU: </b>{product.sku}<br />
                                <b>Price: </b>{product.price}<br />
                                <b>Unit Type: </b>{product.unit_type}<br />
                                <b>Description:</b>{product.product_desc}
                            </Card.Text>
                        </Card.Body>
                    <Col align="right">
                        <br/><br/><br/>
                        <Button variant="link" onClick={this.updateStore}>Update</Button><br/>
                        <Button variant="link" onClick={this.deleteStore}>Delete</Button>
                    </Col>
                </Row>
            </Card>
        );
    }
}

PoolCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolCard));
