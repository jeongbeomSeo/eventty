import React from 'react';
import ReactDOM from 'react-dom/client';
import {RouterProvider} from 'react-router-dom';
import {RecoilRoot} from 'recoil';
import Router from './Router';
import GlobalStyle from './styles/globalStyle';
import {NormalizeCSS} from '@mantine/core';
import {GoogleOAuthProvider} from "@react-oauth/google";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <RecoilRoot>
            <NormalizeCSS/>
            <GlobalStyle/>
            <GoogleOAuthProvider  clientId={"952229162257-3kdoc0aalc3bj4r6uldh3i4996icm608.apps.googleusercontent.com"}>
                <RouterProvider router={Router}/>
            </GoogleOAuthProvider>
        </RecoilRoot>
    </React.StrictMode>
);
