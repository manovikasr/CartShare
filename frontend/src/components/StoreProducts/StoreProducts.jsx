import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import PoolerStoreProducts from "./PoolerStoreProducts";
import AdminStoreProducts from "./AdminStoreProducts";

class StoreProducts extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated) {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
        if (!this.props.location.state) {
            this.props.history.push("/stores");
        }
    }

    render() {
        const { user } = this.props.auth;
        var storeProductsComponent;

        if (user.role === "admin") {
            storeProductsComponent = <AdminStoreProducts />
        } else {
            storeProductsComponent = <PoolerStoreProducts />
        }

        return (
            <div>
                {storeProductsComponent}
            </div>
        );
    }
}

StoreProducts.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(StoreProducts));