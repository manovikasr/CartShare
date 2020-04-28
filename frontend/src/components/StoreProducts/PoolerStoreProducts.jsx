import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";

class PoolerStoreProducts extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    async componentDidMount() {
        if (this.props.location.state) {
            await this.setState({
                store: this.props.location.state.store
            });
            this.getProducts();
        } else {
            this.props.history.push("/stores");
        }
    }

    getProducts = () => {
        if (this.state.store) {
            axios.get(`products/${this.state.store.id}`)
                .then(res => {
                    if (res.data) {
                        this.setState({
                            products: res.data
                        });
                    }
                })
                .catch(e => {
                    console.log(e.response);
                });
        }
    }

    render() {
        const { user } = this.props.auth;

        return (
            <div>
                <b>Store Products</b>
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