import React from 'react'
import { createBrowserRouter } from 'react-router-dom';
import Login from './pages/Login';
import Test from './pages/Test';
import Main from './pages/Main';
import Signup from './pages/Signup';
import SignupMain from './pages/signup/Main';
import SignupHost from './pages/signup/Host';
import Logout from './pages/Logout';
import PublicRoute from './components/PublicRoute';
import PrivateRoute from './components/PrivateRoute';
import SignupMember from './pages/signup/Member';
import Error from './pages/Error';
import Layout from "./components/common/Layout";

const Router = createBrowserRouter([
    {
        path: "",
        element: <Layout />,
        errorElement: <Error />,
        children: [
            {
                path: "",
                element: <Main />,
            }
        ],
    },
    {
        element: <PublicRoute />,
        children: [
            {
                path: "/login",
                element: <Login />,
            },
            {
                path: "/signup",
                element: <Signup />,
                children: [
                    {
                        path: "",
                        element: <SignupMain />
                    },
                    {
                        path: "member",
                        element: <SignupMember />
                    },
                    {
                        path: "host",
                        element: <SignupHost />
                    },
                ]
            },
        ]
    },
    {
        element: <PrivateRoute />,
        children: [
            {
                path: "/logout",
                element: <Logout />,
            },
        ]
    },
    {
        path: "/test",
        element: <Test />
    },
])

export default Router;