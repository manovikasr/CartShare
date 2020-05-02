import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button, Row, Col } from "react-bootstrap";
import AddEditProductModal from "./AddEditProductModal";

class ProductCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
    }

    componentDidMount() {

    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    };

    deleteProduct = e => {
        if (window.confirm("Are you sure you want to delete this Store?")) {
            this.props.deleteProduct(this.props.product.id);
        }
    };

    render() {
        const { user } = this.props.auth;
        const product = this.props.product;

        return (
            <Card bg="white" style={{ width: "45rem", margin: "10%" }}>
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
                        <Button variant="link" onClick={this.handleToggle}>Update</Button><br/>
                        <Button variant="link" onClick={this.deleteProduct}>Delete</Button>
                    </Col>
                </Row>
                <AddEditProductModal store={this.props.store} product={product} showModal={this.state.showModal} onHide={this.handleToggle} getProducts={this.props.getProducts} />
            </Card>
        );
    }
}

ProductCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(ProductCard));
