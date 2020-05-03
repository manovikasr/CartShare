import axios from "axios";
import setAuthToken from "../../config/setAuthToken";
import jwt_decode from "jwt-decode";

import {
  GET_ERRORS,
  SET_CURRENT_USER,
  USER_LOADING,
  GET_SUCCESS_MSG,
  RESET_ALL_STATE,
  RESET_ERROR_STATE,
  CURRENT_USER_INFO
} from "./action-types";

// Login - get user token
axios.defaults.withCredentials = true;
export const loginUser = userData => dispatch => {
  dispatch({
    type: RESET_ALL_STATE
  });
  axios.post("login", userData)
    .then(res => {
      if (res.data.token.length > 0) {
        // Set token to localStorage
        const token = res.data.token;
        localStorage.setItem("jwtToken", token);
        // Set token to Auth header
        setAuthToken(token);
        // Decode token to get user data
        const decoded = jwt_decode(token);
        // Set current user
        dispatch(setCurrentUser(decoded));
      }

    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })
    }
    );
};

export const registerUser = userData => dispatch => {
  dispatch({
    type: RESET_ALL_STATE
  });
  axios.post("register", userData)
    .then(res => {
      if (res.data.token.length > 0) {
        // Set token to localStorage
        const token = res.data.token;
        localStorage.setItem("jwtToken", token);
        // Set token to Auth header
        setAuthToken(token);
        // Decode token to get user data
        const decoded = jwt_decode(token);
        // Set current user
        dispatch(setCurrentUser(decoded));
      }

    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })
    }
    );
};

export const verifyEmail = userData => dispatch => {
  dispatch({
    type: RESET_ALL_STATE
  });
  axios.post(`verify/${userData.email}/${userData.access_code}`)
    .then(res => {
      if (res.data.token.length > 0) {
        // Set token to localStorage
        const token = res.data.token;
        localStorage.setItem("jwtToken", token);
        // Set token to Auth header
        setAuthToken(token);
        // Decode token to get user data
        const decoded = jwt_decode(token);
        // Set current user
        dispatch(setCurrentUser(decoded));
      }

    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })
    }
    );
};

export const getProfile = userData => dispatch => {
  axios
    .get("user/profile/" + userData)
    .then(res => {
      dispatch(setUserInfo(res.data));
    })
}

export const updateProfile = (userData) => {
  return dispatch => {

    axios.post("user/profile", userData)
      .then(resp => {
        if (resp.data) {
          return true;
        } else {

        }
      }, err => {
        // dispatch(stopLoader());

      });
  };
}



export const updateProfile1 = userData => dispatch => {

  axios
    .post("user/profile", userData)
    .then(res => {
      dispatch(setUserInfo({ name: "Namanananan" }))


    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })
    }
    );
}
// Set logged in user
export const setCurrentUser = decoded => {
  return {
    type: SET_CURRENT_USER,
    payload: decoded
  };
};

export const setUserInfo = data => {
  return {
    type: CURRENT_USER_INFO,
    payload: data
  }
}

// User loading
export const setUserLoading = () => {
  return {
    type: USER_LOADING
  };
};

// Logout
export const logoutUser = (history) => dispatch => {
  // Remove token from local storage

  localStorage.removeItem("jwtToken");
  localStorage.removeItem("cart_store_id");
  localStorage.removeItem("cart_items");
  // Remove auth header for future requests
  setAuthToken(false);
  // Set current user to empty object {} which will set isAuthenticated to false
  dispatch(setCurrentUser({}));
  dispatch({
    type: RESET_ALL_STATE
  });

  if (history) {
    history.push({
      pathname: "/",
      comingFrom: "logout"
    });
  }
};

export const updateUser = userInfo => async dispatch => {
  await axios
    .post("http://localhost:3001/user/profile", userInfo)
    .then(response => {
      //dispatch({ type: "SHOW_SUCCESS_MSG" });
    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      });
    });
};

export const fetchManagerProfile = userId => async dispatch => {
  await axios
    .get(`http://localhost:3001/user/profile/${userId}`)
    .then(response => {
      dispatch({ type: "MANAGER_PROFILE_DATA", payload: response.data });
    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      });
    });
};
export const startLoading = () => async dispatch => {
  dispatch({ type: '_REQUEST' })

}
export const endLoading = () => async dispatch => {
  dispatch({ type: '_SUCCESS' })
}
export const fetchTesterprojects = testerId => async dispatch => {

  await axios
    .get(`/project/tester/${testerId}`)
    .then(response => {
      dispatch({ type: "TESTER_PROJECTS", payload: response.data });
    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response
      });
    });
}
