import React, { Component } from "react";
import { withRouter, BrowserRouter, NavLink, Route } from "react-router-dom";
import { Nav } from "react-bootstrap";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import MyPool from "./MyPool";
import AllPools from "./AllPools";

class Pools extends Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    componentDidMount() {
        if (!this.props.auth.isAuthenticated || this.props.auth.user.role === "admin") {
            this.props.history.push("/");
        }
        else {
            if (!this.props.auth.user.email_verified) {
                this.props.history.push("/verify");
            }
        }
    }

    render() {
        return (
            <div>
                <BrowserRouter>
                    <Nav variant="tabs" >
                        <Nav.Item>
                            <Nav.Link eventKey="1" as={NavLink} to="/pool/mypool">
                                My Pool
                        </Nav.Link>
                        </Nav.Item>
                        <Nav.Item>
                            <Nav.Link eventKey="2" as={NavLink} to="/pool/all">
                                All Pools
                        </Nav.Link>
                        </Nav.Item>
                    </Nav>
                    <Route path="/pool/mypool" component={MyPool} exact />
                    <Route path="/pool/all" component={AllPools} exact />
                </BrowserRouter>
            </div>
        );
    }
}

Pools.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(Pools));