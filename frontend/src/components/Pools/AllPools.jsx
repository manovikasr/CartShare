import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Alert, Card, Button, Row, Col } from "react-bootstrap";
import PoolCard from "./PoolCard";

class AllPools extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pools: []
        };
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated && this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
    }

    render() {
        const { user } = this.props.auth;
        var poolData;

        if (this.state.pools.length) {
            poolData = this.state.pools.map(pool => {
                return (<PoolCard pool={pool} />);
            });
        } else {
            poolData = (
                <Alert variant="warning">There are no pools in the system.</Alert>
            )
        }

        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <br />
                {poolData}
            </div>
        );
    }
}

AllPools.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(AllPools));