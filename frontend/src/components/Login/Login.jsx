import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import classnames from "classnames";
import { loginUser } from "../../redux/actions/authActions";

class Login extends Component {
    constructor() {
        super();
        this.state = {
            email_id: "",
            password: "",
            errors: {},
            success: {}
        };
    }

    componentDidMount() {
        if (Object.keys(this.props.success).length > 0) {
            document.getElementById('login-msg-box').style.display = "block";
            document.getElementById('login-msg-box').className = 'alert-success';
            document.getElementById('login-msg-box').innerHTML = "Registration successful!";//this.props.success;
        }
        else {
            document.getElementById('login-msg-box').style.display = "none";
        }

        // If user is already authenticated, redirect to Home
        if (this.props.auth.isAuthenticated) {
            this.props.history.push("/home");
        }
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.auth.isAuthenticated) {
            nextProps.history.push("/home");
        }

        if (Object.keys(nextProps.errors).length > 0) {
            this.setState({
                errors: nextProps.errors
            });
            document.getElementById('login-msg-box').style.display = "block";
            document.getElementById('login-msg-box').className = 'alert-danger';
            document.getElementById('login-msg-box').innerHTML = nextProps.errors;
        }

        if (nextProps.success) {
            this.setState({
                success: nextProps.success
            });

            if (Object.keys(nextProps.success).length > 0) {
                document.getElementById('login-msg-box').style.display = "block";
                document.getElementById('login-msg-box').className = 'alert-success';
                document.getElementById('login-msg-box').innerHTML = nextProps.success;
            }
        }
    }

    onChange = e => {
        this.setState({
            [e.target.id]: e.target.value
        });
        document.getElementById('login-msg-box').style.display = "none";
    };

    onSubmit = e => {
        e.preventDefault();

        const userData = {
            email_id: this.state.email_id,
            password: this.state.password
        };
        this.props.loginUser(userData);
    };


    render() {
        const { errors } = this.state;

        return (
            <React.Fragment>
                <div className="container p-4">

                    <div className="row justify-content-center align-items-center h-100">
                        <div className="col-md-12">

                            {/* <div className="row p-2">
              <div className ="col-md-12 bg-white">
                   <h5 className="text-center" id="output" style={"display:none"}></h5>
              </div>
				   </div> */}
                            <div className="row">
                                <div className="col-md-5 mx-auto bg-white p-3 border rounded">
                                    <h5 className="p-2" id="login-msg-box" style={{ display: 'none' }}> </h5>
                                    <h5 className="p-2 text text-center font-weight-bold">Sign in</h5>
                                    <form onSubmit={this.onSubmit} autoComplete="off">

                                        <div className="row">
                                            <div className="col-md-12">
                                                <div className="form-group">
                                                    <div>
                                                        <span className="text-light-black">Email</span>
                                                    </div>
                                                    <input
                                                        onChange={this.onChange}
                                                        value={this.state.email_id}
                                                        error={errors.email_id}
                                                        id="email_id"
                                                        type="email"
                                                        placeholder="Email"
                                                        email="true"
                                                        required
                                                        minLength="1"
                                                        maxLength="30"
                                                        className={classnames("form-control", {
                                                            invalid: errors.email_id
                                                        })}
                                                    />
                                                    {/* <span className="form-group has-feedback" htmlFor="email">Email</span>
                                      <span className="red-text">{errors.email}</span> */}
                                                </div>
                                            </div>
                                        </div>

                                        <div className="row">
                                            <div className="col-md-12">
                                                <div className="form-group has-feedback">
                                                    <div>
                                                        <span className="text-light-black">Password</span>
                                                    </div>
                                                    <input
                                                        onChange={this.onChange}
                                                        value={this.state.password}
                                                        error={errors.password}
                                                        id="password"
                                                        type="password"
                                                        placeholder="Password"
                                                        required
                                                        pattern="^.+$"
                                                        minLength="1"
                                                        maxLength="10"
                                                        className={classnames("form-control", {
                                                            invalid: errors.password
                                                        })}
                                                    />
                                                    {/* <span className="form-group has-feedback" htmlFor="password">Password</span>
                                      <span className="red-text">{errors.password}</span> */}
                                                </div>
                                            </div>
                                        </div>

                                        <div className="row">
                                            <div className="col-md-12 text-center p-2">
                                                <button
                                                    type="submit"
                                                    className="btn btn-block btn-flat m-btn-primary"
                                                >
                                                    <span>
                                                        <b>Sign In</b>
                                                    </span>
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}

Login.propTypes = {
    loginUser: PropTypes.func.isRequired,
    auth: PropTypes.object.isRequired,
    errors: PropTypes.object.isRequired,
    success: PropTypes.object.isRequired
};

const mapStateToProps = state => ({
    auth: state.auth,
    errors: state.errors,
    success: state.success
});

export default connect(mapStateToProps, { loginUser })(Login);