import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import axios from "axios";
import { Alert, InputGroup, FormControl, Row, Col } from "react-bootstrap";
import PoolCard from "./PoolCard";

class AllPools extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pools: [],
            search_input: "",
            user_pool: {},
            user_applications: []
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
        this.getAllPools();
        this.getUserPool();
        this.getUserApplications();
    }

    componentWillUnmount() {
        this.setState({
            search_input: ""
        });
    }

    onChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    getAllPools = () => {
        axios.get("all")
            .then(res => {
                if (res.data) {
                    this.setState({
                        pools: res.data.pool_list
                    });
                }
            })
            .catch(e => {
                if (e.response) {
                    console.log(e.response.data);
                }
            });
    };

    getUserPool = () => {
        const { user } = this.props.auth;
        axios.get(`user/${user.id}`)
            .then(res => {
                if (res.data && res.data.pool) {
                    this.setState({
                        user_pool: res.data.pool
                    });
                }
            })
            .catch(e => {
                if (e.response)
                    console.log(e.response.data);
            });
    }

    getUserApplications = () => {
        const { user } = this.props.auth;
        axios.get(`application/user/${user.id}`)
            .then(res => {
                if (res.data) {
                    this.setState({
                        user_applications: res.data.pool_Applications_List
                    });
                }
            })
            .catch(e => {
                if (e.response)
                    console.log(e.response.data);
            });
    };

    render() {
        const { user } = this.props.auth;
        var poolData;

        if (this.state.pools.length) {
            const filteredPools = this.state.search_input.length ? this.state.pools.filter(
                pool => pool.pool_name.toLowerCase().includes(this.state.search_input.toLowerCase())
                    || pool.pool_zip.toString().includes(this.state.search_input.toLowerCase())
                    || pool.neighbourhood_name.toLowerCase().includes(this.state.search_input.toLowerCase())
            ) : this.state.pools;

            if (filteredPools.length === 0 && this.state.search_input.length) {
                poolData = (
                    <Alert variant="warning">
                        There are no pools matching your search.
                    </Alert>
                );
            } else {
                poolData = filteredPools.map(pool => {
                    return (
                        <PoolCard
                            pool={pool}
                            alreadyPoolMember={this.state.user_pool.id}
                            applied={this.state.user_applications.map(application => application.reqpoolid).includes(pool.id)}
                            getUserApplications = {this.getUserApplications}
                        />
                    );
                });
            }
        } else {
            poolData = (
                <Alert variant="warning">There are no pools in the system.</Alert>
            )
        }

        return (
            <div style={{ height: "75vh" }} className="container valign-wrapper">
                <br />
                <h2>Join pool</h2>
                <Row>
                    <Col sm={9}>
                        <InputGroup style={{ width: '60%' }} size="lg">
                            <FormControl
                                placeholder="Search Pools by name, neighbourhood or zip.."
                                aria-label="Search Pools"
                                aria-describedby="basic-addon2"
                                style={{ margin: "3%" }}
                                name="search_input"
                                onChange={this.onChange}
                            />
                        </InputGroup>
                    </Col>
                </Row>
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