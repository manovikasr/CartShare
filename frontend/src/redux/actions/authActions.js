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

// Register User
export const signup = (userData, history) => dispatch => {
  axios
    .post("user/signup", userData)
    .then(res => {
                  
                   let  dispatchType = GET_ERRORS;

                    if(res.data)
                          dispatchType = GET_SUCCESS_MSG;

                  dispatch({
                    type: dispatchType,
                    payload: res.data
                  })

                  if(res.data)
                       history.push("/login");

                }
         ) // re-direct to login on successful register
    .catch(err => 
      {
        dispatch({
          type: RESET_ERROR_STATE
        });
        dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })}
    );
};


// Login - get user token
axios.defaults.withCredentials = true;
export const loginUser = userData => dispatch => {
  dispatch({
    type: RESET_ALL_STATE
  });
  axios
    .post("user/login", userData)
    .then(res => {
       
      /*if(res.data.success)
       {*/
          // Save to localStorage// Set token to localStorage
          //console.log("Response ",res);
          const token  = res.data.substring(4);
          localStorage.setItem("jwtToken", token);
          // Set token to Auth header
          setAuthToken(token);
          // Decode token to get user data
          const decoded = jwt_decode(token);
          // Set current user
          dispatch(setCurrentUser(decoded));
      
      
    })
    .catch(err =>
      {
        dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })}
    );
};

export const getProfile = userData =>dispatch=> {
  axios
  .get("user/profile/"+userData)
  .then(res=>{
      dispatch(setUserInfo(res.data));
  })
}

export const updateProfile = (userData) => {
  return dispatch => {
     
      axios.post("user/profile",userData)
      .then(resp => {
        console.log(resp);
          if(resp.data) {
           return true;
          } else {
              
          }
      }, err => {
        console.log("sjjsjs sjsjjs");
         // dispatch(stopLoader());
          
      });
  };
}



export const updateProfile1 = userData =>dispatch=> {
  
  axios
    .post("user/profile", userData)
    .then(res => {
       console.log("upfdted profile");
        
       dispatch(setUserInfo({name:"Namanananan"}))
     
      
    })
    .catch(err =>
      {
        dispatch({
        type: GET_ERRORS,
        payload: err.response.data
      })}
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
  console.log("data is "+data)
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
  // Remove auth header for future requests
  setAuthToken(false);
  // Set current user to empty object {} which will set isAuthenticated to false
  dispatch(setCurrentUser({}));
  dispatch({
    type: RESET_ALL_STATE
  });

  history.push({
    pathname: "/login",
    comingFrom: "logout"
  });
};

export const saveManagerProfile = userInfo => async dispatch => {
  console.log(userInfo);
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
  console.log(userId)
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
export const startLoading = ()=>async dispatch=>{
  console.log("coming here in action")
  dispatch({ type: '_REQUEST' })

}
export const endLoading = ()=>async dispatch=>{
  dispatch({ type: '_SUCCESS' })
}
export const fetchTesterprojects = testerId => async dispatch => {
  console.log(testerId)
  
  await axios
    .get(`/project/tester/${testerId}`)
    .then(response => {
      dispatch({ type: "TESTER_PROJECTS",payload: response.data });
    })
    .catch(err => {
      dispatch({
        type: GET_ERRORS,
        payload: err.response
      });
    });
}
