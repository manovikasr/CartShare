import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, Card, Row, Col, InputGroup, FormControl, Button } from "react-bootstrap";
import ProductCard from "../Products/ProductCard";
import storeImage from "../../images/StoreImage.png";

class PoolerStoreProducts extends Component {
    constructor(props) {
        super(props);
        this.state = {
            store: {},
            products: [],
            search_input: ""
        };
    }

    async componentDidMount() {
        if (this.props.location.state) {
            console.log(this.props.location.state);
            await this.setState({
                store: this.props.location.state.store,
                products: this.props.location.state.store.products
            });
        } else {
            this.props.history.push("/stores");
        }
        const { user } = this.props.auth;
        if (user.role === 'pooler') {
            axios.get(`/pool/user/${user.id}`)
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

    getProducts = () => {
        if (this.state.store) {
            axios.get(`${this.state.store.id}`)
                .then(res => {
                    if (res.data) {
                        this.setState({
                            products: res.data
                        });
                    }
                })
                .catch(e => {
                    if (e.response) {
                        console.log(e.response.data);
                    }
                });
        }
    }

    handleToggle = (e) => {
        this.setState({
            showModal: !this.state.showModal
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    render() {
        var products;
        var store_name, address, address2, alertMessage;
        if (this.state.error_message) {
            alertMessage = (
                <Alert variant="warning">{this.state.error_message}</Alert>
            );
        }
        if (this.state.store) {
            store_name = this.state.store.store_name;
            address = this.state.store.address;
            address2 = this.state.store.city + ", " + this.state.store.state + " - " + this.state.store.zip;
        }
        if (this.state.products.length) {
            const filteredProducts = this.state.search_input.length ? this.state.products.filter(
                product => product.product_name.toLowerCase().includes(this.state.search_input.toLowerCase()) || product.sku.toLowerCase().includes(this.state.search_input.toLowerCase())
            ) : this.state.products;
            if (filteredProducts.length === 0 && this.state.search_input.length) {
                products = (
                    <Alert variant="warning">
                        There are no products matching your search.
                    </Alert>
                );
            } else {
                products = filteredProducts.map(product => {
                    return (
                        <Col sm={3}>
                            <ProductCard store={this.state.store} product={product} getProducts={this.getProducts} isPoolMember={this.state.isPoolMember} />
                        </Col>
                    )
                });
            }
        } else {
            products = (
                <Alert variant="warning">
                    There are no products in this store.
                </Alert>
            )
        }
        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <Card bg="info" text="white" style={{ width: "70rem", height: "15rem", margin: "2%" }}>
                    <Row>
                        <Col>
                            <Card.Img style={{ width: "18rem", height: "15rem" }} src={storeImage} />
                        </Col>
                        <Card.Body>
                            <Card.Title><h1>{store_name}</h1></Card.Title>
                            <br />
                            <Card.Text>
                                <h4>
                                    {address}<br />
                                    {address2}
                                </h4>
                            </Card.Text>
                        </Card.Body>
                    </Row>
                </Card>
                <Row>
                    <Col sm={9}>
                        <InputGroup style={{ width: '50%' }} size="lg">
                            <FormControl
                                placeholder="Search Products.."
                                aria-label="Search Products"
                                aria-describedby="basic-addon2"
                                style={{ margin: "3%" }}
                                name="search_input"
                                onChange={this.onChange}
                            />
                        </InputGroup>
                    </Col>
                </Row>
                {alertMessage}<br />
                {products}
            </div>
        );
    }
}

PoolerStoreProducts.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(PoolerStoreProducts));