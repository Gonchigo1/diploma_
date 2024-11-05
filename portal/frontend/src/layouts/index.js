import DefaultLayout from './default'

const layouts = {
    defaultLayout: DefaultLayout,
}

const renderLayouts = (ls, children) => {
    const Layout = layouts[ls.mainLayout] || layouts.defaultLayout
    return (
        <Layout>
            {ls.render ? ls.render(children) : children}
        </Layout>
    )
}

const LayoutWrapper = (props) => {
    const Layout = layouts[props.layout] || layouts.defaultLayout
    if (props.layouts)
        return renderLayouts(props.layouts, props.children)

    if (Layout != null)
        return <Layout {...props}>{props.children}</Layout>

    return <DefaultLayout {...props}>{props.children}</DefaultLayout>
}

export default LayoutWrapper
