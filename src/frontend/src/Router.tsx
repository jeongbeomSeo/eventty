import React from 'react'
import {createBrowserRouter} from 'react-router-dom';
import Login from './pages/Login';
import Main from './pages/Main';
import Signup from './pages/Signup';
import SignupMain from './pages/signup/SignupMain';
import SignupHost from './pages/signup/SignupHost';
import Logout from './pages/Logout';
import PublicRoute from './components/PublicRoute';
import PrivateRoute from './components/PrivateRoute';
import SignupMember from './pages/signup/SigntupMember';
import Error from './pages/Error';
import WebLayout from "./components/display/web/WebLayout";
import Detail from "./pages/events/EventDetail";
import {loader as eventListLoader} from "./routes/events";
import Events from "./pages/Events";
import ScrollToTop from "./components/ScrollToTop";
import {loader as eventLoader} from "./routes/event";
import Layout from "./components/display/Layout";
import RootSetStates from "./components/RootSetStates";
import Test from "./pages/Test";
import User from "./pages/User";
import EventDetail from "./pages/events/EventDetail";
import Profile from "./pages/user/Profile";
import EventsInfo from "./pages/user/EventsInfo";
import Write from "./pages/Write";

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
                        element: <EventDetail/>,
                        loader: eventLoader,
                    },
                    {
                        element: <PrivateRoute/>,
                        children: [
                            {
                                element:<User/>,
                                children:[
                                    {
                                        path: "users/profile",
                                        element: <Profile/>,
                                    },
                                    {
                                        path: "users/events",
                                        element: <EventsInfo/>,
                                    },
                                    {
                                        path: "users/reservations",
                                    },
                                ]
                            },
                            {
                                path: "events/:eventId/ticket",
                                element: <Test/>,
                            },
                        ]
                    },
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
                path: "write/event",
                element: <Write/>,
            },
            {
                path: "/logout",
                element: <Logout/>,
            },
        ],
    },
]);

export default Router;