import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Card, Button, Row, Col, Modal } from "react-bootstrap";
import AddEditProductModal from "./AddEditProductModal";
import productImage from "../../images/ProductThumbnail.jpg";

class ProductCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false,
            showImageModal: false,
            showCartModal: false,
            purchase_quantity: 1,
            file: null,
            fileText: "Choose image.."
        };
    }

    componentDidMount() {

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
        if (window.confirm("Are you sure you want to delete this Product?")) {
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
                showCartModal: false,
                purchase_quantity: 1
            });
        }
    };

    handleImageClick = (e) => {
        this.setState({
            showImageModal: !this.state.showImageModal
        });
    };

    onImageChange = (e) => {
        this.setState({
            file: e.target.files[0],
            fileText: e.target.files[0].name
        });
    }

    uploadImage = (e) => {
        e.preventDefault();

        const product_id = this.props.product.id;
        const formData = new FormData();
        formData.append("file", this.state.file);
        const uploadConfig = {
            headers: {
                "content-type": "multipart/form-data"
            }
        };
        axios.post(`/upload/product/${product_id}`, formData, uploadConfig)
            .then(response => {
                this.setState({
                    file: null,
                    fileText: "Choose Image..",
                    showImageModal: false
                });
                this.props.getProducts();  
            })
            .catch(err => {
                console.log("Error uploading image");
            });

            // TODO Remove below code
            this.setState({
                file: null,
                fileText: "Choose Image..",
                showImageModal: false
            });
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
        var buttons, store_name, onImageClick;

        // TODO
        var imageSrc = `/image/product/${product.id}`;//product.product_img || productImage;

        if (user.role === 'admin') {
            onImageClick = this.handleImageClick;
            buttons = (
                <>
                    <Button variant="link" onClick={this.handleToggle}>Update</Button><br />
                    <Button variant="link" onClick={this.deleteProduct}>Delete</Button>
                </>
            );
        } else if (user.role === 'pooler') {
            if (this.props.isPoolMember) {
                let buttonText = "Add to Cart";
                let buttonVariant = "primary";
                let buttonClick = this.handleCartModal;
                let cart_items = [];
                let cart_item_ids = [];
                if (localStorage.getItem("cart_items")) {
                    cart_items.push(...JSON.parse(localStorage.getItem("cart_items")));
                    cart_item_ids = cart_items.map(cart_item => cart_item.id);
                    buttonText = cart_item_ids.includes(this.props.product.id) ? "Remove" : buttonText;
                    buttonVariant = cart_item_ids.includes(this.props.product.id) ? "warning" : buttonVariant;
                    buttonClick = cart_item_ids.includes(this.props.product.id) ? this.removeFromCart : buttonClick;
                }
                buttons = (
                    <>
                        <Button variant={buttonVariant} onClick={buttonClick}>{buttonText}</Button>
                        <br />
                    </>
                );
            }
        }
        if (this.props.showStoreName) {
            store_name = (
                <><br />
                    <b>Store: </b>  <Link to={{ pathname: "/store/products", state: { store_id: product.store_id } }}>{product.store_name}</Link>
                </>
            );
        }

        return (

            <div>
                <Card bg="white" style={{ width: "55rem", margin: "10%" }}>
                    <Row>
                        <Col>
                            <Card.Img style={{ width: "12rem", height: "12rem" }} alt="" src={imageSrc} onClick={onImageClick} />
                        </Col>
                        <Card.Body>
                            <Card.Title>{product.product_name}</Card.Title>
                            <Card.Text>
                                <b>Brand: </b>{product.product_brand}<br />
                                <b>SKU: </b>{product.sku}<br />
                                <b>Price: </b>{product.price}<br />
                                <b>Unit Type: </b>{product.unit_type}<br />
                                <b>Description:</b>{product.product_desc}
                                {store_name}
                            </Card.Text>
                        </Card.Body>
                        <Col align="center">
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
                            <img src={imageSrc} width="100%" alt="" height="350"/>
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
                <Modal show={this.state.showImageModal} onHide={this.handleImageClick} centered>
                    <Modal.Header closeButton>
                        <Modal.Title>{this.props.product.product_name}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <center>
                            <img src={imageSrc} width="100%" alt="" height="350"/>
                            <form onSubmit={this.uploadImage}><br /><br /><br />
                                    <div class="custom-file" style={{width: "80%"}}>
                                        <input type="file" class="custom-file-input" name="image" accept="image/*" onChange={this.onImageChange} required/>
                                        <label class="custom-file-label" for="image">{this.state.fileText}</label>
                                    </div><br/><br/>
                                    <Button type="submit" variant="primary">Upload</Button>
                                </form>
                        </center>
                    </Modal.Body>
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
