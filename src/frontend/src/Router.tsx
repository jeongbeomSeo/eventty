import React from 'react'
import {createBrowserRouter, Navigate} from 'react-router-dom';
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
import User from "./pages/User";
import EventDetail from "./pages/events/EventDetail";
import Profile from "./pages/user/Profile";
import EventsInfo from "./pages/user/EventsInfo";
import Write from "./pages/Write";
import SignupUser from "./pages/signup/SignupUser";
import {loader as eventLoader} from "./routes/event";
import {loader as eventListLoader} from "./routes/events";
import {loader as profileLoader} from "./routes/profile";
import {loader as categoryLoader} from "./routes/category";
import {loader as searchLoader} from "./routes/search";
import HostRoute from "./components/HostRoute";
import Bookings from "./pages/user/Bookings";
import EventBooking from "./pages/events/EventBooking";
import EventsList from "./pages/events/EventsList";
import EventsError from "./exception/EventsError";
import Test from "./pages/Test";
import Find from "./pages/Find";
import FindEmail from "./pages/find/FindEmail";
import FindPassword from "./pages/find/FindPassword";

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
                        path: "events/*",
                        element: <Events/>,
                        children: [
                            {
                                path: "",
                                element: <EventsList/>,
                                loader: eventListLoader,
                            },
                            {
                                path: "category/:category",
                                element: <EventsList/>,
                                loader: categoryLoader,
                                errorElement: <EventsError/>,
                            },
                            {
                                path: "search",
                                id: "search",
                                element: <EventsList/>,
                                loader: searchLoader,
                                errorElement: <EventsError/>,
                            }
                        ]
                    },
                    {
                        path: "event/*",
                        id: "event",
                        loader: eventLoader,
                        children: [
                            {
                                path: ":eventId",
                                element: <EventDetail/>,
                            },
                            {
                                element: <PrivateRoute/>,
                                children: [
                                    {
                                        path: ":eventId/booking",
                                        element: <EventBooking/>,
                                    }
                                ]
                            },
                        ]
                    },
                    {
                        element: <PrivateRoute/>,
                        children: [
                            {
                                path: "users/*",
                                element: <User/>,
                                children: [
                                    {
                                        path: "profile",
                                        element: <Profile/>,
                                        loader: profileLoader,
                                        errorElement: <Navigate to={"/login"}/>,
                                    },
                                    {
                                        element: <HostRoute/>,
                                        children: [
                                            {
                                                path: "events",
                                                element: <EventsInfo/>,
                                            },
                                        ]
                                    },
                                    {
                                        path: "bookings",
                                        element: <Bookings/>,
                                    },
                                ]
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
                                path: "user",
                                element: <SignupUser/>
                            },
                            {
                                path: "host",
                                element: <SignupHost/>
                            },
                        ]
                    },
                    {
                        path: "/find/:params",
                        element: <Find/>,
                    }
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