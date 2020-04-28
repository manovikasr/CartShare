import React, { Component } from "react";
import { Link, withRouter } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Card, Button } from "react-bootstrap";
import AdddEditStoreModal from "./AddEditStoreModal";
import storeImage from "../../images/StoreImage.png";

class StoreCard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showModal: false
        };
    }

    componentDidMount() {

    }

    deleteStore = () => {
        if (window.confirm("Are you sure you want to delete this Store?")) {
            this.props.deleteStore(this.props.store.id);
        }
    };

    updateStore = () => {
        this.setState({
            showModal: true
        });
    };

    hideModal = () => {
        this.setState({
            showModal: false
        });
    };

    render() {
        const { user } = this.props.auth;
        var actionButtons;
        if (user.role === "admin") {
            actionButtons = (
                <div>
                    <Button variant="primary" onClick={this.updateStore}>Update</Button>&nbsp;&nbsp;&nbsp;
                    <Button variant="danger" onClick={this.deleteStore}>Delete</Button>
                </div>
            );
        }

        return (
            <Card bg="white" style={{ width: "18rem", margin: "5%" }}>
                <Card.Img
                    variant="top"
                    style={{ height: "15rem" }}
                    src={storeImage}
                />
                <Card.Body>
                    <Link to={{ pathname: "/store/products", state: { store: this.props.store } }}>
                        <center>
                            <Card.Title>{this.props.store.store_name}</Card.Title>
                        </center>
                    </Link>
                    <Card.Text>
                        <b>Address: </b><br />
                        {this.props.store.address}<br />
                        {this.props.store.city}, {this.props.store.state} - {this.props.store.zip}
                    </Card.Text>
                    <center>
                        {actionButtons}
                    </center>
                </Card.Body>
                <AdddEditStoreModal store={this.props.store} showModal={this.state.showModal} onHide={this.hideModal} getStores={this.props.getStores}/>
            </Card>
        );
    }
}

StoreCard.propTypes = {
    auth: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth
});


export default connect(mapStateToProps, {})(withRouter(StoreCard));
