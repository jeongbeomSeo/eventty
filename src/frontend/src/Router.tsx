import React from 'react'
import {createBrowserRouter} from 'react-router-dom';
import Login from './pages/Login';
import Main from './pages/Main';
import Signup from './pages/Signup';
import SignupMain from './pages/signup/Main';
import SignupHost from './pages/signup/Host';
import Logout from './pages/Logout';
import PublicRoute from './components/PublicRoute';
import PrivateRoute from './components/PrivateRoute';
import SignupMember from './pages/signup/Member';
import Error from './pages/Error';
import WebLayout from "./components/display/web/WebLayout";
import Detail from "./pages/events/Detail";
import {loader as eventListLoader} from "./routes/events";
import Events from "./pages/Events";
import ScrollToTop from "./components/ScrollToTop";
import {loader as eventLoader} from "./routes/event";
import Layout from "./components/display/Layout";
import RootSetStates from "./components/RootSetStates";
import Test from "./pages/Test";

const Router = createBrowserRouter([
    {
        path: "",
        element: (
            <RootSetStates/>
        ),
        errorElement: <Error/>,
        children: [
            {
                element: <Layout/>,
                children: [
                    {
                        path: "",
                        element: <Main/>,
                    },
                    {
                        path: "events",
                        element: <Events/>,
                        loader: eventListLoader,
                    },
                    {
                        path: "events/:eventId",
                        element: <Detail/>,
                        loader: eventLoader,
                    }
                ],
            },
            {
                element: <PublicRoute/>,
                children: [
                    {
                        path: "/login",
                        element: <Login/>,
                    },
                    {
                        path: "/signup",
                        element: <Signup/>,
                        children: [
                            {
                                path: "",
                                element: <SignupMain/>
                            },
                            {
                                path: "member",
                                element: <SignupMember/>
                            },
                            {
                                path: "host",
                                element: <SignupHost/>
                            },
                        ]
                    },
                ]
            },
            {
                element: <PrivateRoute/>,
                children: [
                    {
                        path: "/logout",
                        element: <Logout/>,
                    },
                    {
                        path:"events/:eventId/ticket",
                        element: <Test/>,
                    }
                ]
            },
        ],
    },
]);

export default Router;