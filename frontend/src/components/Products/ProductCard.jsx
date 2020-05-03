import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button, Row, Col, Modal } from "react-bootstrap";
import AddEditProductModal from "./AddEditProductModal";
import axios from "axios";

class ProductCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            showCartModal: false,
            isPoolMember: false,
            purchase_quantity: 1
        };
    }

    componentDidMount() {
        const { user } = this.props.auth;
        if (user.role === 'pooler') {
            axios.get("/user/pool")
                .then(res => {
                    if (res.data && res.data.pool && res.data.pool.id) {
                        this.setState({
                            isPoolMember: true
                        });
                    }
                })
                .catch(e => {
                    if (e.response) {
                        console.log(e.response.data)
                    }
                })
        }
    }

    handleToggle = () => {
        this.setState({
            showModal: !this.state.showModal
        });
    };

    handleCartModal = () => {
        this.setState({
            showCartModal: !this.state.showCartModal
        });
    };

    deleteProduct = e => {
        if (window.confirm("Are you sure you want to delete this Store?")) {
            this.props.deleteProduct(this.props.product.id);
        }
    };

    onQuantityChange = (e) => {
        let quantity = parseInt(e.target.value);
        this.setState({
            purchase_quantity: quantity
        });
    };

    addToCart = (e) => {
        let item_id = this.props.product.id;
        let item = this.props.product;
        let cart_items = [];

        if (localStorage.getItem("cart_store_id") && parseInt(localStorage.getItem("cart_store_id")) !== this.props.store.id) {
            var response = window.confirm("You have products from other store in your cart. Do you want to remove them to add this product into the cart?");
            if (response)
                localStorage.setItem("cart_items", cart_items);
            else
                return
        }

        if (localStorage.getItem("cart_items")) {
            cart_items.push(...JSON.parse(localStorage.getItem("cart_items")));
        }

        let index = cart_items.findIndex((cart_item => cart_item.id === item_id));
        if (index === -1) {
            cart_items.push({ ...item, quantity: this.state.purchase_quantity });
            localStorage.setItem("cart_store_id", this.props.store.id);
            localStorage.setItem("cart_items", JSON.stringify(cart_items));
            this.setState({
                showModal: false,
                purchase_quantity: 1
            });
        }
    };

    removeFromCart = (e) => {
        let item_id = this.props.product.id;
        let cart_items = [];

        if (localStorage.getItem("cart_items")) {
            cart_items.push(...JSON.parse(localStorage.getItem("cart_items")));
        }

        let index = cart_items.findIndex((cart_item => cart_item.id === item_id));
        if (index !== -1) {
            e.target.textContent = "Add to Cart";
            e.target.className = "btn btn-primary";
            cart_items.splice(index, 1);
            localStorage.setItem("cart_items", JSON.stringify(cart_items));
            this.setState({
                purchase_quantity: 1
            });
            if (cart_items.length === 0) {
                localStorage.removeItem("cart_store_id");
            }
        }
    };

    render() {
        const { user } = this.props.auth;
        const product = this.props.product;
        var buttons;

        if (user.role === 'admin') {
            buttons = (
                <>
                    <Button variant="link" onClick={this.handleToggle}>Update</Button><br />
                    <Button variant="link" onClick={this.deleteProduct}>Delete</Button>
                </>
            );
        } else if (user.role === 'pooler') {
            if (this.state.isPoolMember) {
                let buttonText = "Add to Cart";
                let buttonVariant = "primary";
                let buttonClick = this.handleCartModal;
                let cart_items = [];
                let cart_item_ids = [];
                if (localStorage.getItem("cart_items")) {
                    cart_items.push(...JSON.parse(localStorage.getItem("cart_items")));
                    cart_item_ids = cart_items.map(cart_item => cart_item.id);
                    buttonText = cart_item_ids.includes(this.props.product.id) ? "Remove from Cart" : buttonText;
                    buttonVariant = cart_item_ids.includes(this.props.product.id) ? "warning" : buttonVariant;
                    buttonClick = cart_item_ids.includes(this.props.product.id) ? this.removeFromCart : buttonClick;
                  }
                buttons = (
                    <>
                        <Button variant="success" onClick={this.handleCartModal}>Add to Cart</Button><br />
                    </>
                );
            }
        }

        return (

            <div>
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
                            <br /><br /><br />
                            {buttons}
                        </Col>
                    </Row>
                    <AddEditProductModal store={this.props.store} product={product} showModal={this.state.showModal} onHide={this.handleToggle} getProducts={this.props.getProducts} />
                </Card>
                <Modal show={this.state.showCartModal} onHide={this.handleCartModal} centered>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.props.product.product_name}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <center>
                            <img src={this.props.product.product_img} width="100%" alt="" />
                            <p>{this.props.product.product_desc}</p>
                            Quantity: <input type="number" name={this.props.product.id} min="1" max="10" width="10%" onChange={this.onQuantityChange} defaultValue="1" autofocus></input>
                        </center>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleCartModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.addToCart}>
                            Add to Cart
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
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
