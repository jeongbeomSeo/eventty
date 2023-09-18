import React from 'react'
import {createBrowserRouter} from 'react-router-dom';
import Login from './pages/Login';
import Main from './pages/Main';
import Signup from './pages/Signup';
import SignupMain from './pages/signup/SignupMain';
import SignupHost from './pages/signup/SignupHost';
import PublicRoute from './components/PublicRoute';
import PrivateRoute from './components/PrivateRoute';
import Error from './pages/Error';
import Events from "./pages/Events";
import Layout from "./components/display/Layout";
import RootSetStates from "./components/RootSetStates";
import Test from "./pages/Test";
import User from "./pages/User";
import EventDetail from "./pages/events/EventDetail";
import Profile from "./pages/user/Profile";
import EventsInfo from "./pages/user/EventsInfo";
import Write from "./pages/Write";
import SignupUser from "./pages/signup/SignupUser";
import {loader as eventLoader} from "./routes/event";
import {loader as eventListLoader} from "./routes/events";
import {loader as profileLoader} from "./routes/profile";
import HostRoute from "./components/HostRoute";
import Bookings from "./pages/user/Bookings";
import EventBooking from "./pages/events/EventBooking";

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
                        path: "events/*",
                        id: "event",
                        loader: eventLoader,
                        children: [
                            {
                                path: ":eventId",
                                element: <EventDetail/>,
                            },
                            {
                                path: ":eventId/booking",
                                element: <EventBooking/>,
                            },
                        ]
                    },
                    {
                        element: <PrivateRoute/>,
                        children: [
                            {
                                element: <User/>,
                                children: [
                                    {
                                        path: "users/profile",
                                        element: <Profile/>,
                                        id: "profile",
                                        loader: profileLoader,
                                    },
                                    {
                                        element: <HostRoute/>,
                                        children: [
                                            {
                                                path: "users/events",
                                                element: <EventsInfo/>,
                                            },
                                        ]
                                    },
                                    {
                                        path: "users/bookings",
                                        element: <Bookings/>,
                                    },
                                ]
                            },
                        ]
                    },
                    {
                        path: "/test",
                        element: <Test/>,
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
                                path: "user",
                                element: <SignupUser/>
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
                        element: <HostRoute/>,
                        children: [
                            {
                                path: "write",
                                element: <Write/>,
                            },
                        ]
                    },
                ]
            },
        ],
    },
]);

export default Router;